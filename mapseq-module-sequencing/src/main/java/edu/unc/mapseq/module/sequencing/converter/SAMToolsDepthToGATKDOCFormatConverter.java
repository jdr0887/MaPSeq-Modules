package edu.unc.mapseq.module.sequencing.converter;

import java.io.File;

import javax.validation.constraints.NotNull;

import edu.unc.mapseq.dao.model.MimeType;
import edu.unc.mapseq.module.Module;
import edu.unc.mapseq.module.annotations.Application;
import edu.unc.mapseq.module.annotations.InputArgument;
import edu.unc.mapseq.module.annotations.InputValidations;
import edu.unc.mapseq.module.annotations.OutputArgument;
import edu.unc.mapseq.module.annotations.OutputValidations;
import edu.unc.mapseq.module.constraints.FileIsNotEmpty;
import edu.unc.mapseq.module.constraints.FileIsReadable;

@Application(name = "SAMToolsDepthToGATKDOCFormatConverter", executable = "$JAVA8_HOME/bin/java -Xmx42g -jar $%s_SEQUENCING_TOOLS/interval-format-converter.jar")
public class SAMToolsDepthToGATKDOCFormatConverter extends Module {

    @NotNull(message = "input is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "input is empty", groups = InputValidations.class)
    @FileIsReadable(message = "input is not readable", groups = InputValidations.class)
    @InputArgument(flag = "-i")
    private File input;

    @InputArgument(flag = "-t")
    private Integer threads;

    @NotNull(message = "intervals is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "intervals is empty", groups = InputValidations.class)
    @FileIsReadable(message = "intervals is not readable", groups = InputValidations.class)
    @InputArgument(flag = "-l")
    private File intervals;

    @NotNull(message = "output is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "output is empty", groups = OutputValidations.class)
    @FileIsReadable(message = "output is not readable", groups = OutputValidations.class)
    @OutputArgument(flag = "-o", mimeType = MimeType.TEXT_PLAIN, persistFileData = true)
    private File output;

    public SAMToolsDepthToGATKDOCFormatConverter() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return SAMToolsDepthToGATKDOCFormatConverter.class;
    }

    @Override
    public String getExecutable() {
        return String.format(getModuleClass().getAnnotation(Application.class).executable(),
                getWorkflowName().toUpperCase());
    }

    public File getInput() {
        return input;
    }

    public void setInput(File input) {
        this.input = input;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public File getIntervals() {
        return intervals;
    }

    public void setIntervals(File intervals) {
        this.intervals = intervals;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public static void main(String[] args) {
        SAMToolsDepthToGATKDOCFormatConverter module = new SAMToolsDepthToGATKDOCFormatConverter();
        module.setWorkflowName("TEST");
        module.setInput(new File("/tmp", "input.sam"));
        module.setOutput(new File("/tmp", "output.txt"));
        module.setIntervals(new File("/tmp", "intervals.txt"));
        try {
            module.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
