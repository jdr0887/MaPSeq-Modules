package edu.unc.mapseq.module.core;

import java.io.File;
import java.util.Arrays;

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

/**
 * 
 * @author jdr0887
 */
@Application(name = "CopyFile2")
public class CopyFile2 extends Module {

    @NotNull(message = "sourcePrefix is required", groups = InputValidations.class)
    @InputArgument
    private String sourcePrefix;

    @NotNull(message = "sourceSuffix is required", groups = InputValidations.class)
    @InputArgument
    private String sourceSuffix;

    @NotNull(message = "sourceParentDir is required", groups = InputValidations.class)
    @InputArgument
    private File sourceParentDir;

    @NotNull(message = "destination is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "invalid output file", groups = OutputValidations.class)
    @InputArgument
    private File destination;

    @InputArgument
    private MimeType mimeType;

    public CopyFile2() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return CopyFile2.class;
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

            File source = Arrays.asList(sourceParentDir.listFiles()).stream()
                    .filter(a -> a.getName().startsWith(sourcePrefix) && a.getName().endsWith(sourceSuffix)).findAny()
                    .orElse(null);

            if (source == null) {
                throw new ModuleException("source not found");
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

    public String getSourcePrefix() {
        return sourcePrefix;
    }

    public void setSourcePrefix(String sourcePrefix) {
        this.sourcePrefix = sourcePrefix;
    }

    public String getSourceSuffix() {
        return sourceSuffix;
    }

    public void setSourceSuffix(String sourceSuffix) {
        this.sourceSuffix = sourceSuffix;
    }

    public File getSourceParentDir() {
        return sourceParentDir;
    }

    public void setSourceParentDir(File sourceParentDir) {
        this.sourceParentDir = sourceParentDir;
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

    public static void main(String[] args) {
        CopyFile2 module = new CopyFile2();
        module.setWorkflowName("TEST");
        module.setSourcePrefix("asdf");
        module.setSourceSuffix("sql");
        module.setSourceParentDir(new File("/tmp"));
        module.setDestination(new File("/tmp", "qwer.sql"));
        System.out.println(module.toString());
        try {
            module.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
