package edu.unc.mapseq.module.sequencing.picard2;

import java.io.File;

import javax.validation.constraints.NotNull;

import edu.unc.mapseq.dao.model.MimeType;
import edu.unc.mapseq.module.Module;
import edu.unc.mapseq.module.annotations.Application;
import edu.unc.mapseq.module.annotations.InputArgument;
import edu.unc.mapseq.module.annotations.InputValidations;
import edu.unc.mapseq.module.annotations.OutputArgument;
import edu.unc.mapseq.module.annotations.OutputValidations;
import edu.unc.mapseq.module.constraints.Contains;
import edu.unc.mapseq.module.constraints.FileIsNotEmpty;
import edu.unc.mapseq.module.constraints.FileIsReadable;

@Application(name = "PicardViewSAM", executable = "$JAVA8_HOME/bin/java -Xmx4g -jar $%s_PICARD2_HOME/picard.jar ViewSam TMP_DIR=$MAPSEQ_CLIENT_HOME/tmp")
public class PicardViewSAM extends Module {

    @NotNull(message = "Input is required", groups = InputValidations.class)
    @FileIsReadable(message = "input file is not readable", groups = InputValidations.class)
    @FileIsNotEmpty(message = "input file is empty", groups = InputValidations.class)
    @InputArgument(flag = "INPUT", delimiter = "=")
    private File input;

    @NotNull(message = "Output is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "output file is empty", groups = OutputValidations.class)
    @OutputArgument(flag = "OUTPUT", delimiter = "=", persistFileData = true, mimeType = MimeType.APPLICATION_BAM)
    private File output;

    @NotNull(message = "alignmentStatus is required", groups = InputValidations.class)
    @Contains(values = { "Aligned", "Unaligned", "All" })
    @InputArgument(flag = "ALIGNMENT_STATUS", delimiter = "=")
    private String alignmentStatus = "All";

    @NotNull(message = "readGroupId is required", groups = InputValidations.class)
    @Contains(values = { "PF", "NonPF", "All" })
    @InputArgument(flag = "PF_STATUS", delimiter = "=")
    private String pfStatus = "All";

    @NotNull(message = "readGroupLibrary is required", groups = InputValidations.class)
    @InputArgument(flag = "HEADER_ONLY", delimiter = "=")
    private Boolean headerOnly = Boolean.FALSE;

    @NotNull(message = "readGroupPlatform is required", groups = InputValidations.class)
    @InputArgument(flag = "RECORDS_ONLY", delimiter = "=")
    private Boolean recordsOnly = Boolean.FALSE;

    @NotNull(message = "readGroupPlatformUnit is required", groups = InputValidations.class)
    @InputArgument(flag = "INTERVAL_LIST", delimiter = "=", wrapValueInSingleQuotes = true)
    private File intervalList;

    @InputArgument(flag = "MAX_RECORDS_IN_RAM", delimiter = "=")
    private Integer maxRecordsInRAM = 1000000;

    public PicardViewSAM() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return PicardViewSAM.class;
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

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public String getAlignmentStatus() {
        return alignmentStatus;
    }

    public void setAlignmentStatus(String alignmentStatus) {
        this.alignmentStatus = alignmentStatus;
    }

    public String getPfStatus() {
        return pfStatus;
    }

    public void setPfStatus(String pfStatus) {
        this.pfStatus = pfStatus;
    }

    public Boolean getHeaderOnly() {
        return headerOnly;
    }

    public void setHeaderOnly(Boolean headerOnly) {
        this.headerOnly = headerOnly;
    }

    public Boolean getRecordsOnly() {
        return recordsOnly;
    }

    public void setRecordsOnly(Boolean recordsOnly) {
        this.recordsOnly = recordsOnly;
    }

    public File getIntervalList() {
        return intervalList;
    }

    public void setIntervalList(File intervalList) {
        this.intervalList = intervalList;
    }

    public Integer getMaxRecordsInRAM() {
        return maxRecordsInRAM;
    }

    public void setMaxRecordsInRAM(Integer maxRecordsInRAM) {
        this.maxRecordsInRAM = maxRecordsInRAM;
    }

    @Override
    public String toString() {
        return String.format(
                "PicardViewSAM [input=%s, output=%s, alignmentStatus=%s, pfStatus=%s, headerOnly=%s, recordsOnly=%s, intervalList=%s, maxRecordsInRAM=%s]",
                input, output, alignmentStatus, pfStatus, headerOnly, recordsOnly, intervalList, maxRecordsInRAM);
    }

    public static void main(String[] args) {
        PicardViewSAM module = new PicardViewSAM();
        module.setWorkflowName("TEST");
        module.setInput(new File("/tmp", "input.sam"));
        module.setOutput(new File("/tmp", "output.bam"));
        try {
            module.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
