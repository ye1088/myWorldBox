package org.apache.tools.ant.taskdefs;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.condition.Os;
import org.apache.tools.ant.taskdefs.launcher.CommandLauncher;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.ant.util.StringUtils;

public class Execute {
    public static final int INVALID = Integer.MAX_VALUE;
    private static final int ONE_SECOND = 1000;
    private static String antWorkingDirectory = System.getProperty("user.dir");
    private static boolean environmentCaseInSensitive;
    private static Map<String, String> procEnvironment = null;
    private static ProcessDestroyer processDestroyer = new ProcessDestroyer();
    private String[] cmdl;
    private String[] env;
    private int exitValue;
    private boolean newEnvironment;
    private Project project;
    private ExecuteStreamHandler streamHandler;
    private boolean useVMLauncher;
    private final ExecuteWatchdog watchdog;
    private File workingDirectory;

    static {
        environmentCaseInSensitive = false;
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            environmentCaseInSensitive = true;
        }
    }

    @Deprecated
    public void setSpawn(boolean spawn) {
    }

    public static synchronized Map<String, String> getEnvironmentVariables() {
        Map<String, String> map;
        synchronized (Execute.class) {
            if (procEnvironment != null) {
                map = procEnvironment;
            } else {
                if (!Os.isFamily(Os.FAMILY_VMS)) {
                    try {
                        procEnvironment = System.getenv();
                        map = procEnvironment;
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }
                procEnvironment = new LinkedHashMap();
                try {
                    BufferedReader in;
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    Execute exe = new Execute(new PumpStreamHandler(out));
                    exe.setCommandline(getProcEnvCommand());
                    exe.setNewenvironment(true);
                    if (exe.execute() != 0) {
                        in = new BufferedReader(new StringReader(toString(out)));
                    } else {
                        in = new BufferedReader(new StringReader(toString(out)));
                    }
                    if (Os.isFamily(Os.FAMILY_VMS)) {
                        procEnvironment = getVMSLogicals(in);
                        map = procEnvironment;
                    } else {
                        int eq;
                        String var = null;
                        String lineSep = StringUtils.LINE_SEP;
                        while (true) {
                            String line = in.readLine();
                            if (line == null) {
                                break;
                            } else if (line.indexOf(61) != -1) {
                                if (var != null) {
                                    eq = var.indexOf(SimpleComparison.EQUAL_TO_OPERATION);
                                    procEnvironment.put(var.substring(0, eq), var.substring(eq + 1));
                                }
                                var = line;
                            } else if (var == null) {
                                var = lineSep + line;
                            } else {
                                var = var + lineSep + line;
                            }
                        }
                        if (var != null) {
                            eq = var.indexOf(SimpleComparison.EQUAL_TO_OPERATION);
                            procEnvironment.put(var.substring(0, eq), var.substring(eq + 1));
                        }
                        map = procEnvironment;
                    }
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }
        return map;
    }

    @Deprecated
    public static synchronized Vector<String> getProcEnvironment() {
        Vector<String> v;
        synchronized (Execute.class) {
            v = new Vector();
            for (Entry<String, String> entry : getEnvironmentVariables().entrySet()) {
                v.add(((String) entry.getKey()) + SimpleComparison.EQUAL_TO_OPERATION + ((String) entry.getValue()));
            }
        }
        return v;
    }

    private static String[] getProcEnvCommand() {
        if (Os.isFamily(Os.FAMILY_OS2)) {
            return new String[]{"cmd", "/c", "set"};
        } else if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            if (Os.isFamily(Os.FAMILY_9X)) {
                return new String[]{"command.com", "/c", "set"};
            }
            return new String[]{"cmd", "/c", "set"};
        } else if (Os.isFamily(Os.FAMILY_ZOS) || Os.isFamily(Os.FAMILY_UNIX)) {
            String[] cmd = new String[1];
            if (new File("/bin/env").canRead()) {
                cmd[0] = "/bin/env";
                return cmd;
            } else if (new File("/usr/bin/env").canRead()) {
                cmd[0] = "/usr/bin/env";
                return cmd;
            } else {
                cmd[0] = "env";
                return cmd;
            }
        } else if (Os.isFamily(Os.FAMILY_NETWARE) || Os.isFamily(Os.FAMILY_OS400)) {
            return new String[]{"env"};
        } else if (!Os.isFamily(Os.FAMILY_VMS)) {
            return null;
        } else {
            return new String[]{"show", "logical"};
        }
    }

    public static String toString(ByteArrayOutputStream bos) {
        if (Os.isFamily(Os.FAMILY_ZOS)) {
            try {
                return bos.toString("Cp1047");
            } catch (UnsupportedEncodingException e) {
            }
        } else {
            if (Os.isFamily(Os.FAMILY_OS400)) {
                try {
                    return bos.toString("Cp500");
                } catch (UnsupportedEncodingException e2) {
                }
            }
            return bos.toString();
        }
    }

    public Execute() {
        this(new PumpStreamHandler(), null);
    }

    public Execute(ExecuteStreamHandler streamHandler) {
        this(streamHandler, null);
    }

    public Execute(ExecuteStreamHandler streamHandler, ExecuteWatchdog watchdog) {
        this.cmdl = null;
        this.env = null;
        this.exitValue = Integer.MAX_VALUE;
        this.workingDirectory = null;
        this.project = null;
        this.newEnvironment = false;
        this.useVMLauncher = true;
        setStreamHandler(streamHandler);
        this.watchdog = watchdog;
        if (Os.isFamily(Os.FAMILY_VMS)) {
            this.useVMLauncher = false;
        }
    }

    public void setStreamHandler(ExecuteStreamHandler streamHandler) {
        this.streamHandler = streamHandler;
    }

    public String[] getCommandline() {
        return this.cmdl;
    }

    public void setCommandline(String[] commandline) {
        this.cmdl = commandline;
    }

    public void setNewenvironment(boolean newenv) {
        this.newEnvironment = newenv;
    }

    public String[] getEnvironment() {
        return (this.env == null || this.newEnvironment) ? this.env : patchEnvironment();
    }

    public void setEnvironment(String[] env) {
        this.env = env;
    }

    public void setWorkingDirectory(File wd) {
        if (wd == null || wd.getAbsolutePath().equals(antWorkingDirectory)) {
            wd = null;
        }
        this.workingDirectory = wd;
    }

    public File getWorkingDirectory() {
        return this.workingDirectory == null ? new File(antWorkingDirectory) : this.workingDirectory;
    }

    public void setAntRun(Project project) throws BuildException {
        this.project = project;
    }

    public void setVMLauncher(boolean useVMLauncher) {
        this.useVMLauncher = useVMLauncher;
    }

    public static Process launch(Project project, String[] command, String[] env, File dir, boolean useVM) throws IOException {
        if (dir == null || dir.exists()) {
            CommandLauncher vmLauncher = CommandLauncher.getVMLauncher(project);
            CommandLauncher launcher = (!useVM || vmLauncher == null) ? CommandLauncher.getShellLauncher(project) : vmLauncher;
            return launcher.exec(project, command, env, dir);
        }
        throw new BuildException(dir + " doesn't exist.");
    }

    public int execute() throws IOException {
        if (this.workingDirectory == null || this.workingDirectory.exists()) {
            Process process = launch(this.project, getCommandline(), getEnvironment(), this.workingDirectory, this.useVMLauncher);
            try {
                this.streamHandler.setProcessInputStream(process.getOutputStream());
                this.streamHandler.setProcessOutputStream(process.getInputStream());
                this.streamHandler.setProcessErrorStream(process.getErrorStream());
                this.streamHandler.start();
                try {
                    processDestroyer.add(process);
                    if (this.watchdog != null) {
                        this.watchdog.start(process);
                    }
                    waitFor(process);
                    if (this.watchdog != null) {
                        this.watchdog.stop();
                    }
                    this.streamHandler.stop();
                    closeStreams(process);
                    if (this.watchdog != null) {
                        this.watchdog.checkException();
                    }
                    int exitValue = getExitValue();
                    processDestroyer.remove(process);
                    return exitValue;
                } catch (ThreadDeath t) {
                    process.destroy();
                    throw t;
                } catch (Throwable th) {
                    processDestroyer.remove(process);
                }
            } catch (IOException e) {
                process.destroy();
                throw e;
            }
        }
        throw new BuildException(this.workingDirectory + " doesn't exist.");
    }

    public void spawn() throws IOException {
        if (this.workingDirectory == null || this.workingDirectory.exists()) {
            Process process = launch(this.project, getCommandline(), getEnvironment(), this.workingDirectory, this.useVMLauncher);
            if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    this.project.log("interruption in the sleep after having spawned a process", 3);
                }
            }
            ExecuteStreamHandler handler = new PumpStreamHandler(new 1(this));
            handler.setProcessErrorStream(process.getErrorStream());
            handler.setProcessOutputStream(process.getInputStream());
            handler.start();
            process.getOutputStream().close();
            this.project.log("spawned process " + process.toString(), 3);
            return;
        }
        throw new BuildException(this.workingDirectory + " doesn't exist.");
    }

    protected void waitFor(Process process) {
        try {
            process.waitFor();
            setExitValue(process.exitValue());
        } catch (InterruptedException e) {
            process.destroy();
        }
    }

    protected void setExitValue(int value) {
        this.exitValue = value;
    }

    public int getExitValue() {
        return this.exitValue;
    }

    public static boolean isFailure(int exitValue) {
        return Os.isFamily(Os.FAMILY_VMS) ? exitValue % 2 == 0 : exitValue != 0;
    }

    public boolean isFailure() {
        return isFailure(getExitValue());
    }

    public boolean killedProcess() {
        return this.watchdog != null && this.watchdog.killedProcess();
    }

    private String[] patchEnvironment() {
        if (Os.isFamily(Os.FAMILY_VMS)) {
            return this.env;
        }
        Map<String, String> osEnv = new LinkedHashMap(getEnvironmentVariables());
        for (String keyValue : this.env) {
            String key = keyValue.substring(0, keyValue.indexOf(61));
            if (osEnv.remove(key) == null && environmentCaseInSensitive) {
                for (String osEnvItem : osEnv.keySet()) {
                    if (osEnvItem.toLowerCase().equals(key.toLowerCase())) {
                        key = osEnvItem;
                        break;
                    }
                }
            }
            osEnv.put(key, keyValue.substring(key.length() + 1));
        }
        ArrayList<String> l = new ArrayList();
        for (Entry<String, String> entry : osEnv.entrySet()) {
            l.add(((String) entry.getKey()) + SimpleComparison.EQUAL_TO_OPERATION + ((String) entry.getValue()));
        }
        return (String[]) l.toArray(new String[osEnv.size()]);
    }

    public static void runCommand(Task task, String[] cmdline) throws BuildException {
        try {
            task.log(Commandline.describeCommand(cmdline), 3);
            Execute exe = new Execute(new LogStreamHandler(task, 2, 0));
            exe.setAntRun(task.getProject());
            exe.setCommandline(cmdline);
            int retval = exe.execute();
            if (isFailure(retval)) {
                throw new BuildException(cmdline[0] + " failed with return code " + retval, task.getLocation());
            }
        } catch (IOException exc) {
            throw new BuildException("Could not launch " + cmdline[0] + ": " + exc, task.getLocation());
        }
    }

    public static void closeStreams(Process process) {
        FileUtils.close(process.getInputStream());
        FileUtils.close(process.getOutputStream());
        FileUtils.close(process.getErrorStream());
    }

    private static Map<String, String> getVMSLogicals(BufferedReader in) throws IOException {
        HashMap<String, String> logicals = new HashMap();
        String logName = null;
        String logValue = null;
        while (true) {
            String line = in.readLine();
            if (line == null) {
                break;
            } else if (line.startsWith("\t=")) {
                if (logName != null) {
                    logValue = logValue + MiPushClient.ACCEPT_TIME_SEPARATOR + line.substring(4, line.length() - 1);
                }
            } else if (line.startsWith("  \"")) {
                if (logName != null) {
                    logicals.put(logName, logValue);
                }
                int eqIndex = line.indexOf(61);
                String newLogName = line.substring(3, eqIndex - 2);
                if (logicals.containsKey(newLogName)) {
                    logName = null;
                } else {
                    logName = newLogName;
                    logValue = line.substring(eqIndex + 3, line.length() - 1);
                }
            }
        }
        if (logName != null) {
            logicals.put(logName, logValue);
        }
        return logicals;
    }
}
