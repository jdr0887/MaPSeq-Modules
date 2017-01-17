package edu.unc.mapseq.module.sequencing.casava;

import java.io.File;

import javax.validation.constraints.NotNull;

import edu.unc.mapseq.module.Module;
import edu.unc.mapseq.module.annotations.Application;
import edu.unc.mapseq.module.annotations.InputArgument;
import edu.unc.mapseq.module.annotations.InputValidations;
import edu.unc.mapseq.module.annotations.OutputArgument;
import edu.unc.mapseq.module.constraints.FileIsNotEmpty;
import edu.unc.mapseq.module.constraints.FileIsReadable;

@Application(name = "ConfigureBCLToFastQ", executable = "$PERL_HOME/bin/perl $%s_CASAVA_HOME/bin/configureBclToFastq.pl")
public class ConfigureBCLToFastq extends Module {

    @NotNull(message = "outputDir is required", groups = InputValidations.class)
    @OutputArgument(flag = "--output-dir", delimiter = " ")
    private File outputDir;

    @InputArgument(flag = "--fastq-cluster-count", delimiter = " ")
    private Integer fastqClusterCount;

    @NotNull(message = "inputDir is required", groups = InputValidations.class)
    @FileIsReadable(message = "inputDir is not readable", groups = InputValidations.class)
    @InputArgument(flag = "--input-dir", delimiter = " ")
    private File inputDir;

    @NotNull(message = "sampleSheet is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "sampleSheet is empty", groups = InputValidations.class)
    @FileIsReadable(message = "sampleSheet is not readable", groups = InputValidations.class)
    @InputArgument(flag = "--sample-sheet", delimiter = " ")
    private File sampleSheet;

    @InputArgument(flag = "--force")
    private Boolean force;

    @InputArgument(flag = "--ignore-missing-bcl")
    private Boolean ignoreMissingBCL;

    @InputArgument(flag = "--mismatches")
    private Integer mismatches = 0;

    @InputArgument(flag = "--ignore-missing-stats")
    private Boolean ignoreMissingStats;

    @InputArgument(flag = "--use-bases-mask")
    private String basesMask;

    @InputArgument(flag = "--tiles", delimiter = " ")
    private String tiles;

    public ConfigureBCLToFastq() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return ConfigureBCLToFastq.class;
    }

    public String getExecutable() {
        return String.format(getModuleClass().getAnnotation(Application.class).executable(),
                getWorkflowName().toUpperCase());
    }

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public File getInputDir() {
        return inputDir;
    }

    public void setInputDir(File inputDir) {
        this.inputDir = inputDir;
    }

    public File getSampleSheet() {
        return sampleSheet;
    }

    public void setSampleSheet(File sampleSheet) {
        this.sampleSheet = sampleSheet;
    }

    public Boolean getForce() {
        return force;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public Integer getFastqClusterCount() {
        return fastqClusterCount;
    }

    public void setFastqClusterCount(Integer fastqClusterCount) {
        this.fastqClusterCount = fastqClusterCount;
    }

    public String getTiles() {
        return tiles;
    }

    public void setTiles(String tiles) {
        this.tiles = tiles;
    }

    public Integer getMismatches() {
        return mismatches;
    }

    public void setMismatches(Integer mismatches) {
        this.mismatches = mismatches;
    }

    public Boolean getIgnoreMissingBCL() {
        return ignoreMissingBCL;
    }

    public void setIgnoreMissingBCL(Boolean ignoreMissingBCL) {
        this.ignoreMissingBCL = ignoreMissingBCL;
    }

    public Boolean getIgnoreMissingStats() {
        return ignoreMissingStats;
    }

    public void setIgnoreMissingStats(Boolean ignoreMissingStats) {
        this.ignoreMissingStats = ignoreMissingStats;
    }

    public String getBasesMask() {
        return basesMask;
    }

    public void setBasesMask(String basesMask) {
        this.basesMask = basesMask;
    }

    @Override
    public String toString() {
        return String.format(
                "ConfigureBCLToFastq [outputDir=%s, fastqClusterCount=%s, inputDir=%s, sampleSheet=%s, force=%s, ignoreMissingBCL=%s, mismatches=%s, ignoreMissingStats=%s, basesMask=%s, tiles=%s]",
                outputDir, fastqClusterCount, inputDir, sampleSheet, force, ignoreMissingBCL, mismatches,
                ignoreMissingStats, basesMask, tiles);
    }

    public static void main(String[] args) {
        ConfigureBCLToFastq module = new ConfigureBCLToFastq();
        module.setWorkflowName("TEST");

        module.setInputDir(new File("inputdir"));
        module.setMismatches(1);
        module.setIgnoreMissingBCL(Boolean.TRUE);
        module.setIgnoreMissingStats(Boolean.TRUE);
        module.setFastqClusterCount(0);
        module.setBasesMask("Y*,I7,Y*");
        module.setTiles("s_1_*");
        module.setOutputDir(new File("outputdir"));
        module.setSampleSheet(new File("/tmp", "samplesheet.csv"));
        module.setForce(Boolean.TRUE);

        try {
            module.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
