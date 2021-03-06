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

@Application(name = "PicardSortSAM", executable = "$JAVA8_HOME/bin/java -Xmx4g -jar $%s_PICARD2_HOME/picard.jar SortSam TMP_DIR=$MAPSEQ_CLIENT_HOME/tmp VALIDATION_STRINGENCY=SILENT")
public class PicardSortSAM extends Module {

    @NotNull(message = "Input is required", groups = InputValidations.class)
    @FileIsReadable(message = "input file is not readable", groups = InputValidations.class)
    @FileIsNotEmpty(message = "input file is empty", groups = InputValidations.class)
    @InputArgument(flag = "INPUT", delimiter = "=", description = "The BAM or SAM file to sort.")
    private File input;

    @NotNull(message = "Output is required", groups = InputValidations.class)
    @FileIsReadable(message = "output file is not readable", groups = OutputValidations.class)
    @FileIsNotEmpty(message = "output file is empty", groups = OutputValidations.class)
    @OutputArgument(flag = "OUTPUT", delimiter = "=", persistFileData = true, mimeType = MimeType.APPLICATION_BAM)
    private File output;

    @NotNull(message = "Output is required", groups = InputValidations.class)
    @Contains(values = { "unsorted", "queryname", "coordinate" })
    @InputArgument(flag = "SORT_ORDER", delimiter = "=", description = "The sorted BAM or SAM output file.")
    private String sortOrder;

    @InputArgument(flag = "MAX_RECORDS_IN_RAM", delimiter = "=")
    private Integer maxRecordsInRAM = 1000000;

    @InputArgument(flag = "CREATE_INDEX", delimiter = "=")
    private Boolean createIndex = Boolean.FALSE;

    public PicardSortSAM() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return PicardSortSAM.class;
    }

    @Override
    public String getExecutable() {
        return String.format(getModuleClass().getAnnotation(Application.class).executable(), getWorkflowName().toUpperCase());
    }

    public Boolean getCreateIndex() {
        return createIndex;
    }

    public void setCreateIndex(Boolean createIndex) {
        this.createIndex = createIndex;
    }

    public Integer getMaxRecordsInRAM() {
        return maxRecordsInRAM;
    }

    public void setMaxRecordsInRAM(Integer maxRecordsInRAM) {
        this.maxRecordsInRAM = maxRecordsInRAM;
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

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return String.format("PicardSortSAM [input=%s, output=%s, sortOrder=%s, maxRecordsInRAM=%s, toString()=%s]", input, output,
                sortOrder, maxRecordsInRAM, super.toString());
    }

}
