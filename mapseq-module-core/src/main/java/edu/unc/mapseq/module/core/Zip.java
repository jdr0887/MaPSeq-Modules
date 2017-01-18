package edu.unc.mapseq.module.core;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.renci.common.exec.BashExecutor;
import org.renci.common.exec.CommandInput;
import org.renci.common.exec.CommandOutput;
import org.renci.common.exec.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.unc.mapseq.dao.model.FileData;
import edu.unc.mapseq.dao.model.MimeType;
import edu.unc.mapseq.module.Module;
import edu.unc.mapseq.module.ModuleException;
import edu.unc.mapseq.module.ModuleOutput;
import edu.unc.mapseq.module.ShellModuleOutput;
import edu.unc.mapseq.module.annotations.Application;
import edu.unc.mapseq.module.annotations.InputArgument;
import edu.unc.mapseq.module.annotations.InputValidations;
import edu.unc.mapseq.module.annotations.OutputArgument;
import edu.unc.mapseq.module.annotations.OutputValidations;
import edu.unc.mapseq.module.constraints.FileIsReadable;
import edu.unc.mapseq.module.constraints.FileListIsReadable;

/**
 * 
 * @author jdr0887
 * 
 */
@Application(name = "Zip", isWorkflowRunIdOptional = true, executable = "/usr/bin/zip")
public class Zip extends Module {

    private static final Logger logger = LoggerFactory.getLogger(Zip.class);

    @NotNull(message = "workDir is required", groups = InputValidations.class)
    @InputArgument
    private File workDir;

    @NotNull(message = "Zip is required", groups = InputValidations.class)
    @FileIsReadable(message = "Zip file does is not readable", groups = OutputValidations.class)
    @OutputArgument(persistFileData = true, mimeType = MimeType.APPLICATION_ZIP)
    private File output;

    @NotNull(message = "Entry is required", groups = InputValidations.class)
    @FileListIsReadable(message = "One or more entries is not readable", groups = InputValidations.class)
    @InputArgument(description = "Files to zip")
    private List<File> entry;

    public Zip() {
        super();
    }

    @Override
    public String getExecutable() {
        return getModuleClass().getAnnotation(Application.class).executable();
    }

    @Override
    public Class<?> getModuleClass() {
        return Zip.class;
    }

    @Override
    public ModuleOutput call() throws Exception {
        logger.debug("ENTERING call()");
        ShellModuleOutput ret = null;
        try {

            StringBuilder command = new StringBuilder(getModuleClass().getAnnotation(Application.class).executable());
            command.append(" ").append(output.getAbsolutePath());
            for (File f : entry) {
                command.append(" ").append(f.getName());
            }

            CommandInput commandInput = new CommandInput();
            commandInput.setWorkDir(workDir);
            commandInput.setCommand(command.toString());

            Executor executor = BashExecutor.getInstance();
            CommandOutput commandOutput = executor.execute(commandInput);

            ret = new ShellModuleOutput(commandOutput);

            FileData fileData = new FileData(output.getName(), output.getParentFile().getAbsolutePath(),
                    MimeType.APPLICATION_ZIP);
            getFileDatas().add(fileData);

        } catch (Exception e) {
            throw new ModuleException(e);
        }
        return ret;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public List<File> getEntry() {
        return entry;
    }

    public void setEntry(List<File> entry) {
        this.entry = entry;
    }

    public File getWorkDir() {
        return workDir;
    }

    public void setWorkDir(File workDir) {
        this.workDir = workDir;
    }

    @Override
    public String toString() {
        return String.format("Zip [output=%s, entry=%s, toString()=%s]", output, entry, super.toString());
    }

    public static void main(String[] args) {
        try {
            Zip module = new Zip();
            module.setWorkflowName("TEST");
            module.setWorkDir(new File(System.getProperty("java.io.tmpdir")));
            module.setEntry(Arrays.asList(new File("/tmp", "asdf.txt"), new File("/tmp", "zxcv.txt")));
            module.setOutput(new File("/tmp", "asdf.zip"));
            ModuleOutput output = module.call();
            System.out.println(output.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
