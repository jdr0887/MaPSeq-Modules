package edu.unc.mapseq.module.sequencing.vcflib;

import java.io.File;

import javax.validation.constraints.NotNull;

import edu.unc.mapseq.module.Module;
import edu.unc.mapseq.module.annotations.Application;
import edu.unc.mapseq.module.annotations.InputArgument;
import edu.unc.mapseq.module.annotations.InputValidations;
import edu.unc.mapseq.module.annotations.OutputArgument;

@Application(name = "VCFFilter", executable = "cat %1$s | $%2$s_VCFLIB_HOME/bin/vcfstreamsort | $%2$s_VCFLIB_HOME/bin/vcfuniq")
public class SortAndRemoveDuplicates extends Module {

    @NotNull(message = "input is required", groups = InputValidations.class)
    @InputArgument(disabled = true)
    private File input;

    @NotNull(message = "output is required", groups = InputValidations.class)
    @OutputArgument(redirect = true)
    private File output;

    public SortAndRemoveDuplicates() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return SortAndRemoveDuplicates.class;
    }

    @Override
    public String getExecutable() {
        return String.format(getModuleClass().getAnnotation(Application.class).executable(), input.getAbsolutePath(),
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
        return String.format("VCFFilter [input=%s, output=%s]", input, output);
    }

    public static void main(String[] args) {
        try {
            SortAndRemoveDuplicates module = new SortAndRemoveDuplicates();
            module.setWorkflowName("TEST");
            module.setInput(new File("/tmp", "input.vcf"));
            module.setOutput(new File("/tmp", "output.vcf"));
            module.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
