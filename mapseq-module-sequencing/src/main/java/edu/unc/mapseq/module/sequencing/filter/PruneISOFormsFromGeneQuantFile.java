package edu.unc.mapseq.module.sequencing.filter;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.renci.common.exec.BashExecutor;
import org.renci.common.exec.CommandInput;
import org.renci.common.exec.CommandOutput;
import org.renci.common.exec.Executor;
import org.renci.common.exec.ExecutorException;

import edu.unc.mapseq.module.Module;
import edu.unc.mapseq.module.ModuleException;
import edu.unc.mapseq.module.ModuleOutput;
import edu.unc.mapseq.module.ShellModuleOutput;
import edu.unc.mapseq.module.annotations.Application;
import edu.unc.mapseq.module.annotations.InputArgument;
import edu.unc.mapseq.module.annotations.InputValidations;

@Application(name = "PruneISOFormsFromGeneQuantFile", executable = "/bin/mv %1$s %2$s; sed /^uc0/d %2$s > %1$s")
public class PruneISOFormsFromGeneQuantFile extends Module {

    @NotNull(message = "geneResults is required", groups = InputValidations.class)
    @InputArgument
    private File geneResults;

    @NotNull(message = "origGeneResults is required", groups = InputValidations.class)
    @InputArgument
    private File origGeneResults;

    @Override
    public ModuleOutput call() throws Exception {
        CommandInput commandInput = new CommandInput();
        commandInput.setCommand(String.format(getModuleClass().getAnnotation(Application.class).executable(),
                geneResults.getAbsolutePath(), origGeneResults.getAbsolutePath()));
        CommandOutput commandOutput;
        try {
            Executor executor = BashExecutor.getInstance();
            commandOutput = executor.execute(commandInput);
        } catch (ExecutorException e) {
            throw new ModuleException(e);
        }
        return new ShellModuleOutput(commandOutput);
    }

    @Override
    public Class<?> getModuleClass() {
        return PruneISOFormsFromGeneQuantFile.class;
    }

    public File getGeneResults() {
        return geneResults;
    }

    public void setGeneResults(File geneResults) {
        this.geneResults = geneResults;
    }

    public File getOrigGeneResults() {
        return origGeneResults;
    }

    public void setOrigGeneResults(File origGeneResults) {
        this.origGeneResults = origGeneResults;
    }

    @Override
    public String toString() {
        return String.format("PruneISOFormsFromGeneQuantFile [geneResults=%s, origGeneResults=%s, toString()=%s]",
                geneResults, origGeneResults, super.toString());
    }

}
