package edu.unc.mapseq.module.sequencing.mapsplice;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.renci.common.exec.BashExecutor;
import org.renci.common.exec.CommandInput;
import org.renci.common.exec.CommandOutput;
import org.renci.common.exec.Executor;
import org.renci.common.exec.ExecutorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.unc.mapseq.module.Module;
import edu.unc.mapseq.module.ModuleException;
import edu.unc.mapseq.module.ModuleOutput;
import edu.unc.mapseq.module.ShellModuleOutput;
import edu.unc.mapseq.module.annotations.Application;
import edu.unc.mapseq.module.annotations.InputArgument;
import edu.unc.mapseq.module.annotations.InputValidations;
import edu.unc.mapseq.module.annotations.OutputValidations;
import edu.unc.mapseq.module.constraints.FileIsReadable;

@Application(name = "JunctionDBFusion", executable = "$%s_MAPSPLICE_HOME/bin/junc_db_fusion")
public class JunctionDBFusion extends Module {

    private final Logger logger = LoggerFactory.getLogger(JunctionDBFusion.class);

    @NotNull(message = "minimumAnchorWidth is required", groups = InputValidations.class)
    @InputArgument
    private Integer minimumAnchorWidth;

    @NotNull(message = "maximumAnchor is required", groups = InputValidations.class)
    @InputArgument
    private Integer maximumAnchor;

    @NotNull(message = "maximumThresholdEach is required", groups = InputValidations.class)
    @InputArgument
    private Integer maximumThresholdEach;

    @NotNull(message = "maximumThresholdTotal is required", groups = InputValidations.class)
    @InputArgument
    private Integer maximumThresholdTotal;

    @NotNull(message = "junction is required", groups = InputValidations.class)
    @FileIsReadable(message = "junction does not exist or is not readable", groups = InputValidations.class)
    @InputArgument
    private File junction;

    @NotNull(message = "fusionJunction is required", groups = InputValidations.class)
    @FileIsReadable(message = "fusionJunction does not exist or is not readable", groups = InputValidations.class)
    @InputArgument
    private File fusionJunction;

    @NotNull(message = "referenceSequenceDirectory is required", groups = InputValidations.class)
    @FileIsReadable(message = "referenceSequenceDirectory does not exist or is not readable", groups = InputValidations.class)
    @InputArgument
    private File referenceSequenceDirectory;

    @NotNull(message = "output is required", groups = InputValidations.class)
    @FileIsReadable(message = "output does not exist or is not readable", groups = OutputValidations.class)
    @InputArgument
    private File output;

    @Override
    public Class<?> getModuleClass() {
        return JunctionDBFusion.class;
    }

    @Override
    public String getExecutable() {
        return String.format(getModuleClass().getAnnotation(Application.class).executable(),
                getWorkflowName().toUpperCase());
    }

    @Override
    public ModuleOutput call() throws Exception {
        logger.debug("ENTERING call()");

        StringBuilder command = new StringBuilder(getExecutable());
        command.append(" ").append(this.minimumAnchorWidth.toString());
        command.append(" ").append(this.maximumAnchor.toString());
        command.append(" ").append(this.maximumThresholdEach.toString());
        command.append(" ").append(this.maximumThresholdTotal.toString());
        command.append(" ").append(this.junction.getAbsolutePath());
        command.append(" 1");
        command.append(" ").append(this.fusionJunction.getAbsolutePath());
        command.append(" 1");
        command.append(" ").append(this.referenceSequenceDirectory.getAbsolutePath());
        if (this.referenceSequenceDirectory.isDirectory()
                && !this.referenceSequenceDirectory.getAbsolutePath().endsWith("/")) {
            command.append("/");
        }
        command.append(" ").append(this.output.getAbsolutePath());

        CommandInput commandInput = new CommandInput();
        logger.info("command.toString(): {}", command.toString());
        commandInput.setCommand(command.toString());
        CommandOutput commandOutput;
        try {
            Executor executor = BashExecutor.getInstance();
            commandOutput = executor.execute(commandInput);
        } catch (ExecutorException e) {
            throw new ModuleException(e);
        }
        return new ShellModuleOutput(commandOutput);
    }

    public Integer getMinimumAnchorWidth() {
        return minimumAnchorWidth;
    }

    public void setMinimumAnchorWidth(Integer minimumAnchorWidth) {
        this.minimumAnchorWidth = minimumAnchorWidth;
    }

    public Integer getMaximumAnchor() {
        return maximumAnchor;
    }

    public void setMaximumAnchor(Integer maximumAnchor) {
        this.maximumAnchor = maximumAnchor;
    }

    public Integer getMaximumThresholdEach() {
        return maximumThresholdEach;
    }

    public void setMaximumThresholdEach(Integer maximumThresholdEach) {
        this.maximumThresholdEach = maximumThresholdEach;
    }

    public Integer getMaximumThresholdTotal() {
        return maximumThresholdTotal;
    }

    public void setMaximumThresholdTotal(Integer maximumThresholdTotal) {
        this.maximumThresholdTotal = maximumThresholdTotal;
    }

    public File getJunction() {
        return junction;
    }

    public void setJunction(File junction) {
        this.junction = junction;
    }

    public File getFusionJunction() {
        return fusionJunction;
    }

    public void setFusionJunction(File fusionJunction) {
        this.fusionJunction = fusionJunction;
    }

    public File getReferenceSequenceDirectory() {
        return referenceSequenceDirectory;
    }

    public void setReferenceSequenceDirectory(File referenceSequenceDirectory) {
        this.referenceSequenceDirectory = referenceSequenceDirectory;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return String.format(
                "JunctionDBFusion [logger=%s, minimumAnchorWidth=%s, maximumAnchor=%s, maximumThresholdEach=%s, maximumThresholdTotal=%s, junction=%s, fusionJunction=%s, referenceSequenceDirectory=%s, output=%s, toString()=%s]",
                logger, minimumAnchorWidth, maximumAnchor, maximumThresholdEach, maximumThresholdTotal, junction,
                fusionJunction, referenceSequenceDirectory, output, super.toString());
    }

}
