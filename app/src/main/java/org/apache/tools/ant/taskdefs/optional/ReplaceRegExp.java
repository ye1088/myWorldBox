package org.apache.tools.ant.taskdefs.optional;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.optional.vss.MSVSSConstants;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.RegularExpression;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;
import org.apache.tools.ant.types.Substitution;
import org.apache.tools.ant.types.resources.FileProvider;
import org.apache.tools.ant.types.resources.Union;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.ant.util.regexp.Regexp;
import org.apache.tools.ant.util.regexp.RegexpUtil;

public class ReplaceRegExp extends Task {
    private static final FileUtils FILE_UTILS = FileUtils.getFileUtils();
    private boolean byline = false;
    private String encoding = null;
    private File file = null;
    private String flags = "";
    private boolean preserveLastModified = false;
    private RegularExpression regex = null;
    private Union resources;
    private Substitution subs = null;

    public void setFile(File file) {
        this.file = file;
    }

    public void setMatch(String match) {
        if (this.regex != null) {
            throw new BuildException("Only one regular expression is allowed");
        }
        this.regex = new RegularExpression();
        this.regex.setPattern(match);
    }

    public void setReplace(String replace) {
        if (this.subs != null) {
            throw new BuildException("Only one substitution expression is allowed");
        }
        this.subs = new Substitution();
        this.subs.setExpression(replace);
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    @Deprecated
    public void setByLine(String byline) {
        Boolean res = Boolean.valueOf(byline);
        if (res == null) {
            res = Boolean.FALSE;
        }
        this.byline = res.booleanValue();
    }

    public void setByLine(boolean byline) {
        this.byline = byline;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void addFileset(FileSet set) {
        addConfigured(set);
    }

    public void addConfigured(ResourceCollection rc) {
        if (rc.isFilesystemOnly()) {
            if (this.resources == null) {
                this.resources = new Union();
            }
            this.resources.add(rc);
            return;
        }
        throw new BuildException("only filesystem resources are supported");
    }

    public RegularExpression createRegexp() {
        if (this.regex != null) {
            throw new BuildException("Only one regular expression is allowed.");
        }
        this.regex = new RegularExpression();
        return this.regex;
    }

    public Substitution createSubstitution() {
        if (this.subs != null) {
            throw new BuildException("Only one substitution expression is allowed");
        }
        this.subs = new Substitution();
        return this.subs;
    }

    public void setPreserveLastModified(boolean b) {
        this.preserveLastModified = b;
    }

    protected String doReplace(RegularExpression r, Substitution s, String input, int options) {
        String res = input;
        Regexp regexp = r.getRegexp(getProject());
        if (!regexp.matches(input, options)) {
            return res;
        }
        log("Found match; substituting", 4);
        return regexp.substitute(input, s.getExpression(getProject()), options);
    }

    protected void doReplace(File f, int options) throws IOException {
        Throwable th;
        File temp = FILE_UTILS.createTempFile(MSVSSConstants.WRITABLE_REPLACE, ".txt", null, true, true);
        boolean changes = false;
        InputStream is;
        try {
            Reader inputStreamReader;
            is = new FileInputStream(f);
            if (this.encoding != null) {
                inputStreamReader = new InputStreamReader(is, this.encoding);
            } else {
                inputStreamReader = new InputStreamReader(is);
            }
            OutputStream fileOutputStream = new FileOutputStream(temp);
            try {
                Writer outputStreamWriter;
                String str;
                Reader r;
                Writer w;
                if (this.encoding != null) {
                    outputStreamWriter = new OutputStreamWriter(fileOutputStream, this.encoding);
                } else {
                    outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                }
                StringBuilder append = new StringBuilder().append("Replacing pattern '").append(this.regex.getPattern(getProject())).append("' with '").append(this.subs.getExpression(getProject())).append("' in '").append(f.getPath()).append("'").append(this.byline ? " by line" : "");
                if (this.flags.length() > 0) {
                    str = " with flags: '" + this.flags + "'";
                } else {
                    str = "";
                }
                log(append.append(str).append(".").toString(), 3);
                if (this.byline) {
                    inputStreamReader = new BufferedReader(r);
                    try {
                        outputStreamWriter = new BufferedWriter(w);
                        StringBuffer linebuf = new StringBuffer();
                        boolean hasCR = false;
                        int c;
                        do {
                            c = inputStreamReader.read();
                            if (c == 13) {
                                if (hasCR) {
                                    changes |= replaceAndWrite(linebuf.toString(), outputStreamWriter, options);
                                    outputStreamWriter.write(13);
                                    linebuf = new StringBuffer();
                                    continue;
                                } else {
                                    hasCR = true;
                                    continue;
                                }
                            } else if (c == 10) {
                                changes |= replaceAndWrite(linebuf.toString(), outputStreamWriter, options);
                                if (hasCR) {
                                    outputStreamWriter.write(13);
                                    hasCR = false;
                                }
                                outputStreamWriter.write(10);
                                linebuf = new StringBuffer();
                                continue;
                            } else {
                                if (hasCR || c < 0) {
                                    changes |= replaceAndWrite(linebuf.toString(), outputStreamWriter, options);
                                    if (hasCR) {
                                        outputStreamWriter.write(13);
                                        hasCR = false;
                                    }
                                    linebuf = new StringBuffer();
                                }
                                if (c >= 0) {
                                    linebuf.append((char) c);
                                    continue;
                                } else {
                                    continue;
                                }
                            }
                        } while (c >= 0);
                        w = outputStreamWriter;
                        r = inputStreamReader;
                    } catch (Throwable th2) {
                        th = th2;
                        r = inputStreamReader;
                    }
                } else {
                    changes = multilineReplace(r, w, options);
                }
                r.close();
                w.close();
                fileOutputStream.close();
                is.close();
                if (changes) {
                    log("File has changed; saving the updated file", 3);
                    long origLastModified = f.lastModified();
                    FILE_UTILS.rename(temp, f);
                    if (this.preserveLastModified) {
                        FILE_UTILS.setFileLastModified(f, origLastModified);
                    }
                    temp = null;
                } else {
                    log("No change made", 4);
                }
                if (temp != null) {
                    temp.delete();
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream.close();
                throw th;
            }
        } catch (IOException e) {
            throw new BuildException("Couldn't rename temporary file " + temp, e, getLocation());
        } catch (Throwable th4) {
            if (temp != null) {
                temp.delete();
            }
        }
    }

    public void execute() throws BuildException {
        if (this.regex == null) {
            throw new BuildException("No expression to match.");
        } else if (this.subs == null) {
            throw new BuildException("Nothing to replace expression with.");
        } else if (this.file == null || this.resources == null) {
            int options = RegexpUtil.asOptions(this.flags);
            if (this.file != null && this.file.exists()) {
                try {
                    doReplace(this.file, options);
                } catch (IOException e) {
                    log("An error occurred processing file: '" + this.file.getAbsolutePath() + "': " + e.toString(), 0);
                }
            } else if (this.file != null) {
                log("The following file is missing: '" + this.file.getAbsolutePath() + "'", 0);
            }
            if (this.resources != null) {
                Iterator i$ = this.resources.iterator();
                while (i$.hasNext()) {
                    File f = ((FileProvider) ((Resource) i$.next()).as(FileProvider.class)).getFile();
                    if (f.exists()) {
                        try {
                            doReplace(f, options);
                        } catch (Exception e2) {
                            log("An error occurred processing file: '" + f.getAbsolutePath() + "': " + e2.toString(), 0);
                        }
                    } else {
                        log("The following file is missing: '" + f.getAbsolutePath() + "'", 0);
                    }
                }
            }
        } else {
            throw new BuildException("You cannot supply the 'file' attribute and resource collections at the same time.");
        }
    }

    private boolean multilineReplace(Reader r, Writer w, int options) throws IOException {
        return replaceAndWrite(FileUtils.safeReadFully(r), w, options);
    }

    private boolean replaceAndWrite(String s, Writer w, int options) throws IOException {
        String res = doReplace(this.regex, this.subs, s, options);
        w.write(res);
        return !res.equals(s);
    }
}
