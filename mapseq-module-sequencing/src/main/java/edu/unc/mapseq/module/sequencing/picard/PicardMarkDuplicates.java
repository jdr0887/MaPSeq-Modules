package edu.unc.mapseq.module.sequencing.picard;

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

@Application(name = "PicardMarkDuplicates", executable = "$JAVA7_HOME/bin/java -Xmx4g -jar $%s_PICARD_HOME/picard.jar MarkDuplicates TMP_DIR=$MAPSEQ_CLIENT_HOME/tmp")
public class PicardMarkDuplicates extends Module {

    @NotNull(message = "Input is required", groups = InputValidations.class)
    @FileIsReadable(message = "input file is not readable", groups = InputValidations.class)
    @FileIsNotEmpty(message = "input file is empty", groups = InputValidations.class)
    @InputArgument(flag = "INPUT", delimiter = "=")
    private File input;

    @NotNull(message = "Output is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "output file is empty", groups = OutputValidations.class)
    @OutputArgument(flag = "OUTPUT", delimiter = "=", persistFileData = true, mimeType = MimeType.APPLICATION_BAM)
    private File output;

    @NotNull(message = "metricsFile is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "metricsFile file is empty", groups = OutputValidations.class)
    @OutputArgument(flag = "METRICS_FILE", delimiter = "=", persistFileData = true, mimeType = MimeType.PICARD_MARK_DUPLICATE_METRICS)
    private File metricsFile;

    @InputArgument(flag = "MAX_RECORDS_IN_RAM", delimiter = "=")
    private Integer maxRecordsInRAM = 1000000;

    @InputArgument(flag = "VALIDATION_STRINGENCY", delimiter = "=")
    private String validationStringency = "SILENT";

    @InputArgument(flag = "REMOVE_DUPLICATES", delimiter = "=")
    private String removeDuplicates = Boolean.FALSE.toString().toLowerCase();

    public PicardMarkDuplicates() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return PicardMarkDuplicates.class;
    }

    @Override
    public String getExecutable() {
        return String.format(getModuleClass().getAnnotation(Application.class).executable(),
                getWorkflowName().toUpperCase());
    }

    public Integer getMaxRecordsInRAM() {
        return maxRecordsInRAM;
    }

    public void setMaxRecordsInRAM(Integer maxRecordsInRAM) {
        this.maxRecordsInRAM = maxRecordsInRAM;
    }

    public String getValidationStringency() {
        return validationStringency;
    }

    public void setValidationStringency(String validationStringency) {
        this.validationStringency = validationStringency;
    }

    public String getRemoveDuplicates() {
        return removeDuplicates;
    }

    public void setRemoveDuplicates(String removeDuplicates) {
        this.removeDuplicates = removeDuplicates;
    }

    public File getInput() {
        return input;
    }

    public void setInput(File input) {
        this.input = input;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public File getMetricsFile() {
        return metricsFile;
    }

    public void setMetricsFile(File metricsFile) {
        this.metricsFile = metricsFile;
    }

    @Override
    public String toString() {
        return String.format(
                "PicardMarkDuplicates [input=%s, output=%s, metricsFile=%s, maxRecordsInRAM=%s, toString()=%s]", input,
                output, metricsFile, maxRecordsInRAM, super.toString());
    }

}
