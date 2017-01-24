package edu.unc.mapseq.module.sequencing.vcflib;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.renci.common.exec.BashExecutor;
import org.renci.common.exec.CommandInput;
import org.renci.common.exec.CommandOutput;
import org.renci.common.exec.Executor;
import org.renci.common.exec.ExecutorException;

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

@Application(name = "VCFFilter", executable = "$%s_VCFLIB_HOME/bin/vcffilter")
public class VCFFilter extends Module {

    @NotNull(message = "input is required", groups = InputValidations.class)
    @InputArgument(order = 99, flag = "")
    private File input;

    @NotNull(message = "output is required", groups = InputValidations.class)
    @OutputArgument(redirect = true)
    private File output;

    @InputArgument(flag = "--info-filter", wrapValueInSingleQuotes = true)
    private List<String> infoFilter;

    @InputArgument(flag = "--genotype-filter", wrapValueInSingleQuotes = true)
    private List<String> genotypeFilter;

    @InputArgument(flag = "--keep-info")
    private Boolean keepInfo;

    @InputArgument(flag = "--filter-sites")
    private Boolean filterSites;

    @InputArgument(flag = "--tag-pass")
    private String tagPass;

    @InputArgument(flag = "--tag-fail")
    private String tagFail;

    @InputArgument(flag = "--append-filter")
    private Boolean appendFilter;

    @InputArgument(flag = "--invert")
    private Boolean invert;

    @InputArgument(flag = "--or")
    private Boolean or;

    public VCFFilter() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return VCFFilter.class;
    }

    @Override
    public String getExecutable() {
        return String.format(getModuleClass().getAnnotation(Application.class).executable(),
                getWorkflowName().toUpperCase());
    }

    @Override
    public ModuleOutput call() throws Exception {
        CommandInput commandInput = new CommandInput();
        StringBuilder command = new StringBuilder(getExecutable());

        if (CollectionUtils.isNotEmpty(infoFilter)) {
            infoFilter.forEach(a -> command.append(" --info-filter '").append(a.replaceAll("_", " ")).append("'"));
        }

        if (CollectionUtils.isNotEmpty(genotypeFilter)) {
            genotypeFilter
                    .forEach(a -> command.append(" --genotype-filter '").append(a.replaceAll("_", " ")).append("'"));
        }

        if (keepInfo != null && keepInfo) {
            command.append(" --keep-info");
        }

        if (filterSites != null && filterSites) {
            command.append(" --filter-sites");
        }

        if (appendFilter != null && appendFilter) {
            command.append(" --append-filter");
        }

        if (invert != null && invert) {
            command.append(" --invert");
        }

        if (or != null && or) {
            command.append(" --or");
        }

        if (StringUtils.isNotEmpty(tagFail)) {
            command.append(" --tag-fail '").append(tagFail).append("'");
        }

        if (StringUtils.isNotEmpty(tagPass)) {
            command.append(" --tag-pass '").append(tagPass).append("'");
        }

        command.append(String.format(" %s > %s", input.getAbsolutePath(), output.getAbsolutePath()));
        commandInput.setCommand(command.toString());
        CommandOutput commandOutput;
        try {
            Executor executor = BashExecutor.getInstance();
            commandOutput = executor.execute(commandInput);
        } catch (ExecutorException e) {
            throw new ModuleException(e);
        }
        FileData fm = new FileData();
        fm.setMimeType(MimeType.TEXT_VCF);
        fm.setName(output.getName());
        getFileDatas().add(fm);
        return new ShellModuleOutput(commandOutput);
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

    public List<String> getInfoFilter() {
        return infoFilter;
    }

    public void setInfoFilter(List<String> infoFilter) {
        this.infoFilter = infoFilter;
    }

    public List<String> getGenotypeFilter() {
        return genotypeFilter;
    }

    public void setGenotypeFilter(List<String> genotypeFilter) {
        this.genotypeFilter = genotypeFilter;
    }

    public Boolean getKeepInfo() {
        return keepInfo;
    }

    public void setKeepInfo(Boolean keepInfo) {
        this.keepInfo = keepInfo;
    }

    public Boolean getFilterSites() {
        return filterSites;
    }

    public void setFilterSites(Boolean filterSites) {
        this.filterSites = filterSites;
    }

    public String getTagPass() {
        return tagPass;
    }

    public void setTagPass(String tagPass) {
        this.tagPass = tagPass;
    }

    public String getTagFail() {
        return tagFail;
    }

    public void setTagFail(String tagFail) {
        this.tagFail = tagFail;
    }

    public Boolean getAppendFilter() {
        return appendFilter;
    }

    public void setAppendFilter(Boolean appendFilter) {
        this.appendFilter = appendFilter;
    }

    public Boolean getInvert() {
        return invert;
    }

    public void setInvert(Boolean invert) {
        this.invert = invert;
    }

    public Boolean getOr() {
        return or;
    }

    public void setOr(Boolean or) {
        this.or = or;
    }

    @Override
    public String toString() {
        return String.format(
                "VCFFilter [input=%s, output=%s, infoFilter=%s, genotypeFilter=%s, keepInfo=%s, filterSites=%s, tagPass=%s, tagFail=%s, appendFilter=%s, invert=%s, or=%s]",
                input, output, infoFilter, genotypeFilter, keepInfo, filterSites, tagPass, tagFail, appendFilter,
                invert, or);
    }

    public static void main(String[] args) {
        try {
            VCFFilter module = new VCFFilter();
            module.setWorkflowName("TEST");
            module.setInput(new File("/tmp", "input.vcf"));
            module.setOutput(new File("/tmp", "output.vcf"));
            module.setInfoFilter(Arrays.asList("QUAL_>_10", "DP_>_5"));
            module.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
