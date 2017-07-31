package org.apache.tools.ant.taskdefs;

import java.io.File;
import java.io.IOException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.ZipFileSet;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.zip.ZipOutputStream;

public class War extends Jar {
    private static final FileUtils FILE_UTILS = FileUtils.getFileUtils();
    private static final String XML_DESCRIPTOR_PATH = "WEB-INF/web.xml";
    private File addedWebXmlFile;
    private File deploymentDescriptor;
    private boolean needxmlfile;

    public War() {
        this.needxmlfile = true;
        this.archiveType = "war";
        this.emptyBehavior = "create";
    }

    @Deprecated
    public void setWarfile(File warFile) {
        setDestFile(warFile);
    }

    public void setWebxml(File descr) {
        this.deploymentDescriptor = descr;
        if (this.deploymentDescriptor.exists()) {
            ZipFileSet fs = new ZipFileSet();
            fs.setFile(this.deploymentDescriptor);
            fs.setFullpath(XML_DESCRIPTOR_PATH);
            super.addFileset(fs);
            return;
        }
        throw new BuildException("Deployment descriptor: " + this.deploymentDescriptor + DirectoryScanner.DOES_NOT_EXIST_POSTFIX);
    }

    public void setNeedxmlfile(boolean needxmlfile) {
        this.needxmlfile = needxmlfile;
    }

    public void addLib(ZipFileSet fs) {
        fs.setPrefix("WEB-INF/lib/");
        super.addFileset(fs);
    }

    public void addClasses(ZipFileSet fs) {
        fs.setPrefix("WEB-INF/classes/");
        super.addFileset(fs);
    }

    public void addWebinf(ZipFileSet fs) {
        fs.setPrefix("WEB-INF/");
        super.addFileset(fs);
    }

    protected void initZipOutputStream(ZipOutputStream zOut) throws IOException, BuildException {
        super.initZipOutputStream(zOut);
    }

    protected void zipFile(File file, ZipOutputStream zOut, String vPath, int mode) throws IOException {
        boolean addFile = true;
        if (XML_DESCRIPTOR_PATH.equalsIgnoreCase(vPath)) {
            if (this.addedWebXmlFile != null) {
                addFile = false;
                if (!FILE_UTILS.fileNameEquals(this.addedWebXmlFile, file)) {
                    logWhenWriting("Warning: selected " + this.archiveType + " files include a second " + XML_DESCRIPTOR_PATH + " which will be ignored.\n" + "The duplicate entry is at " + file + '\n' + "The file that will be used is " + this.addedWebXmlFile, 1);
                }
            } else {
                this.addedWebXmlFile = file;
                addFile = true;
                this.deploymentDescriptor = file;
            }
        }
        if (addFile) {
            super.zipFile(file, zOut, vPath, mode);
        }
    }

    protected void cleanUp() {
        if (this.addedWebXmlFile == null && this.deploymentDescriptor == null && this.needxmlfile && !isInUpdateMode() && hasUpdatedFile()) {
            throw new BuildException("No WEB-INF/web.xml file was added.\nIf this is your intent, set needxmlfile='false' ");
        }
        this.addedWebXmlFile = null;
        super.cleanUp();
    }
}
