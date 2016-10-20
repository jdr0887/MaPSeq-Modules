package edu.unc.mapseq.module.core;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FileUtils;

import edu.unc.mapseq.dao.model.FileData;
import edu.unc.mapseq.dao.model.MimeType;
import edu.unc.mapseq.module.DefaultModuleOutput;
import edu.unc.mapseq.module.Module;
import edu.unc.mapseq.module.ModuleException;
import edu.unc.mapseq.module.ModuleOutput;
import edu.unc.mapseq.module.annotations.Application;
import edu.unc.mapseq.module.annotations.InputArgument;
import edu.unc.mapseq.module.annotations.InputValidations;
import edu.unc.mapseq.module.annotations.OutputValidations;
import edu.unc.mapseq.module.constraints.FileIsNotEmpty;
import edu.unc.mapseq.module.constraints.FileIsReadable;

/**
 * 
 * @author jdr0887
 */
@Application(name = "CopyFile")
public class CopyFile extends Module {

    @NotNull(message = "source is required", groups = InputValidations.class)
    @FileIsReadable(message = "source does not exist", groups = InputValidations.class)
    @InputArgument
    private File source;

    @NotNull(message = "destination is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "invalid output file", groups = OutputValidations.class)
    @InputArgument
    private File destination;

    @InputArgument
    private MimeType mimeType;

    public CopyFile() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return CopyFile.class;
    }

    @Override
    public ModuleOutput call() throws ModuleException {
        DefaultModuleOutput moduleOutput = new DefaultModuleOutput();
        try {

            if (destination.exists()) {
                FileUtils.forceDelete(destination);
            }

            if (!destination.getParentFile().exists()) {
                destination.getParentFile().mkdirs();
            }

            FileUtils.copyFile(source, destination);

            if (mimeType != null) {
                FileData fileData = new FileData();
                fileData.setName(destination.getName());
                fileData.setMimeType(mimeType);
                getFileDatas().add(fileData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            moduleOutput.setError(new StringBuilder(e.getMessage()));
            moduleOutput.setExitCode(-1);
        }
        moduleOutput.setExitCode(0);
        return moduleOutput;
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public File getDestination() {
        return destination;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String toString() {
        return String.format("CopyFile [source=%s, destination=%s, mimeType=%s, toString()=%s]", source, destination,
                mimeType, super.toString());
    }

    public static void main(String[] args) {
        CopyFile module = new CopyFile();
        module.setWorkflowName("TEST");
        module.setSource(new File("/tmp", "asdf.txt"));
        module.setDestination(new File("/tmp", "qwer.txt"));
        System.out.println(module.toString());
        try {
            module.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
