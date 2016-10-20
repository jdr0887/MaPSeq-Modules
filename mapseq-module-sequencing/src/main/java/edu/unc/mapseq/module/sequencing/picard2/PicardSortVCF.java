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
import edu.unc.mapseq.module.constraints.FileIsNotEmpty;
import edu.unc.mapseq.module.constraints.FileIsReadable;

@Application(name = "PicardSortVCF", executable = "$JAVA7_HOME/bin/java -Xmx4g -jar $%s_PICARD2_HOME/picard.jar SortVcf")
public class PicardSortVCF extends Module {

    @NotNull(message = "Input is required", groups = InputValidations.class)
    @FileIsReadable(message = "input file is not readable", groups = InputValidations.class)
    @FileIsNotEmpty(message = "input file is empty", groups = InputValidations.class)
    @InputArgument(flag = "INPUT", delimiter = "=", description = "The VCF file to sort.")
    private File input;

    @NotNull(message = "Output is required", groups = InputValidations.class)
    @FileIsReadable(message = "output file is not readable", groups = OutputValidations.class)
    @FileIsNotEmpty(message = "output file is empty", groups = OutputValidations.class)
    @OutputArgument(flag = "OUTPUT", delimiter = "=", persistFileData = true, mimeType = MimeType.TEXT_VCF)
    private File output;

    public PicardSortVCF() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return PicardSortVCF.class;
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

    @Override
    public String toString() {
        return String.format("PicardSortVCF [input=%s, output=%s]", input, output);
    }

    public static void main(String[] args) {
        try {
            PicardSortVCF module = new PicardSortVCF();
            module.setWorkflowName("TEST");
            module.setInput(new File("/tmp", "asdf.vcf"));
            module.setOutput(new File("/tmp", "asdf.sorted.vcf"));
            module.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
