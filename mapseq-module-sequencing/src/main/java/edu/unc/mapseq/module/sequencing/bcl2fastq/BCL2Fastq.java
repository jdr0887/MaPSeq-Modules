package edu.unc.mapseq.module.sequencing.bcl2fastq;

import java.io.File;

import javax.validation.constraints.NotNull;

import edu.unc.mapseq.module.Module;
import edu.unc.mapseq.module.annotations.Application;
import edu.unc.mapseq.module.annotations.InputArgument;
import edu.unc.mapseq.module.annotations.InputValidations;
import edu.unc.mapseq.module.annotations.OutputArgument;
import edu.unc.mapseq.module.constraints.FileIsNotEmpty;
import edu.unc.mapseq.module.constraints.FileIsReadable;

@Application(name = "BCL2FastQ", executable = "$%s_BCL2FASTQ_HOME/bin/bcl2fastq")
public class BCL2Fastq extends Module {

    @NotNull(message = "outputDir is required", groups = InputValidations.class)
    @OutputArgument(flag = "--output-dir", delimiter = " ")
    private File outputDir;

    @NotNull(message = "inputDir is required", groups = InputValidations.class)
    @FileIsReadable(message = "inputDir is not readable", groups = InputValidations.class)
    @InputArgument(flag = "--input-dir", delimiter = " ")
    private File inputDir;

    @NotNull(message = "runFolderDir is required", groups = InputValidations.class)
    @FileIsReadable(message = "runFolderDir is not readable", groups = InputValidations.class)
    @InputArgument(flag = "--runfolder-dir", delimiter = " ")
    private File runFolderDir;

    @NotNull(message = "sampleSheet is required", groups = InputValidations.class)
    @FileIsNotEmpty(message = "sampleSheet is empty", groups = InputValidations.class)
    @FileIsReadable(message = "sampleSheet is not readable", groups = InputValidations.class)
    @InputArgument(flag = "--sample-sheet", delimiter = " ")
    private File sampleSheet;

    @FileIsReadable(message = "intensities dir is not readable", groups = InputValidations.class)
    @InputArgument(flag = "--intensities-dir", delimiter = " ")
    private File intensitiesDir;

    @FileIsReadable(message = "interop dir is not readable", groups = InputValidations.class)
    @InputArgument(flag = "--interop-dir", delimiter = " ")
    private File interopDir;

    @FileIsReadable(message = "stats dir is not readable", groups = InputValidations.class)
    @InputArgument(flag = "--stats-dir", delimiter = " ")
    private File statsDir;

    @FileIsReadable(message = "reports dir is not readable", groups = InputValidations.class)
    @InputArgument(flag = "--reports-dir", delimiter = " ")
    private File reportsDir;

    @InputArgument(flag = "--loading-threads")
    private Integer loadingThreads;

    @InputArgument(flag = "--processing-threads")
    private Integer processingThreads;

    @InputArgument(flag = "--writing-threads")
    private Integer writingThreads;

    @InputArgument(flag = "--tiles", delimiter = " ")
    private String tiles;

    @InputArgument(flag = "--minimum-trimmed-read-length")
    private Integer minimumTrimmedReadLength;

    @InputArgument(flag = "--use-bases-mask")
    private String usesBasesMask;

    @InputArgument(flag = "--mask-short-adapter-reads")
    private Integer maskShortAdapterReads;

    @InputArgument(flag = "--adapter-stringency")
    private Double adapterStringency;

    @InputArgument(flag = "--ignore-missing-bcls")
    private Boolean ignoreMissingBCLs;

    @InputArgument(flag = "--ignore-missing-filter")
    private Boolean ignoreMissingFilter;

    @InputArgument(flag = "--ignore-missing-positions")
    private Boolean ignoreMissingPositions;

    @InputArgument(flag = "--ignore-missing-controls")
    private Boolean ignoreMissingControls;

    @InputArgument(flag = "--write-fastq-reverse-complement")
    private Boolean writeFastqReverseComplement;

    @InputArgument(flag = "--with-failed-reads")
    private Boolean withFailedReads;

    @InputArgument(flag = "--create-fastq-for-index-reads")
    private Boolean createFastqForIndexReads;

    @InputArgument(flag = "--find-adapters-with-sliding-window")
    private Boolean findAdaptersWithSlidingWindow;

    @InputArgument(flag = "--no-bgzf-compression")
    private Boolean noBGZFCompression;

    @InputArgument(flag = "--fastq-compression-level")
    private Integer fastqCompressionLevel;

    @InputArgument(flag = "--barcode-mismatches")
    private Integer barcodeMismatches;

    @InputArgument(flag = "--no-lane-splitting")
    private Boolean noLaneSplitting;

    public BCL2Fastq() {
        super();
    }

    @Override
    public Class<?> getModuleClass() {
        return BCL2Fastq.class;
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

    public File getRunFolderDir() {
        return runFolderDir;
    }

    public void setRunFolderDir(File runFolderDir) {
        this.runFolderDir = runFolderDir;
    }

    public File getSampleSheet() {
        return sampleSheet;
    }

    public void setSampleSheet(File sampleSheet) {
        this.sampleSheet = sampleSheet;
    }

    public File getIntensitiesDir() {
        return intensitiesDir;
    }

    public void setIntensitiesDir(File intensitiesDir) {
        this.intensitiesDir = intensitiesDir;
    }

    public File getInteropDir() {
        return interopDir;
    }

    public void setInteropDir(File interopDir) {
        this.interopDir = interopDir;
    }

    public File getStatsDir() {
        return statsDir;
    }

    public void setStatsDir(File statsDir) {
        this.statsDir = statsDir;
    }

    public File getReportsDir() {
        return reportsDir;
    }

    public void setReportsDir(File reportsDir) {
        this.reportsDir = reportsDir;
    }

    public Integer getLoadingThreads() {
        return loadingThreads;
    }

    public void setLoadingThreads(Integer loadingThreads) {
        this.loadingThreads = loadingThreads;
    }

    public Integer getProcessingThreads() {
        return processingThreads;
    }

    public void setProcessingThreads(Integer processingThreads) {
        this.processingThreads = processingThreads;
    }

    public Integer getWritingThreads() {
        return writingThreads;
    }

    public void setWritingThreads(Integer writingThreads) {
        this.writingThreads = writingThreads;
    }

    public String getTiles() {
        return tiles;
    }

    public void setTiles(String tiles) {
        this.tiles = tiles;
    }

    public Integer getMinimumTrimmedReadLength() {
        return minimumTrimmedReadLength;
    }

    public void setMinimumTrimmedReadLength(Integer minimumTrimmedReadLength) {
        this.minimumTrimmedReadLength = minimumTrimmedReadLength;
    }

    public String getUsesBasesMask() {
        return usesBasesMask;
    }

    public void setUsesBasesMask(String usesBasesMask) {
        this.usesBasesMask = usesBasesMask;
    }

    public Integer getMaskShortAdapterReads() {
        return maskShortAdapterReads;
    }

    public void setMaskShortAdapterReads(Integer maskShortAdapterReads) {
        this.maskShortAdapterReads = maskShortAdapterReads;
    }

    public Double getAdapterStringency() {
        return adapterStringency;
    }

    public void setAdapterStringency(Double adapterStringency) {
        this.adapterStringency = adapterStringency;
    }

    public Boolean getIgnoreMissingBCLs() {
        return ignoreMissingBCLs;
    }

    public void setIgnoreMissingBCLs(Boolean ignoreMissingBCLs) {
        this.ignoreMissingBCLs = ignoreMissingBCLs;
    }

    public Boolean getIgnoreMissingFilter() {
        return ignoreMissingFilter;
    }

    public void setIgnoreMissingFilter(Boolean ignoreMissingFilter) {
        this.ignoreMissingFilter = ignoreMissingFilter;
    }

    public Boolean getIgnoreMissingPositions() {
        return ignoreMissingPositions;
    }

    public void setIgnoreMissingPositions(Boolean ignoreMissingPositions) {
        this.ignoreMissingPositions = ignoreMissingPositions;
    }

    public Boolean getIgnoreMissingControls() {
        return ignoreMissingControls;
    }

    public void setIgnoreMissingControls(Boolean ignoreMissingControls) {
        this.ignoreMissingControls = ignoreMissingControls;
    }

    public Boolean getWriteFastqReverseComplement() {
        return writeFastqReverseComplement;
    }

    public void setWriteFastqReverseComplement(Boolean writeFastqReverseComplement) {
        this.writeFastqReverseComplement = writeFastqReverseComplement;
    }

    public Boolean getWithFailedReads() {
        return withFailedReads;
    }

    public void setWithFailedReads(Boolean withFailedReads) {
        this.withFailedReads = withFailedReads;
    }

    public Boolean getCreateFastqForIndexReads() {
        return createFastqForIndexReads;
    }

    public void setCreateFastqForIndexReads(Boolean createFastqForIndexReads) {
        this.createFastqForIndexReads = createFastqForIndexReads;
    }

    public Boolean getFindAdaptersWithSlidingWindow() {
        return findAdaptersWithSlidingWindow;
    }

    public void setFindAdaptersWithSlidingWindow(Boolean findAdaptersWithSlidingWindow) {
        this.findAdaptersWithSlidingWindow = findAdaptersWithSlidingWindow;
    }

    public Boolean getNoBGZFCompression() {
        return noBGZFCompression;
    }

    public void setNoBGZFCompression(Boolean noBGZFCompression) {
        this.noBGZFCompression = noBGZFCompression;
    }

    public Integer getFastqCompressionLevel() {
        return fastqCompressionLevel;
    }

    public void setFastqCompressionLevel(Integer fastqCompressionLevel) {
        this.fastqCompressionLevel = fastqCompressionLevel;
    }

    public Integer getBarcodeMismatches() {
        return barcodeMismatches;
    }

    public void setBarcodeMismatches(Integer barcodeMismatches) {
        this.barcodeMismatches = barcodeMismatches;
    }

    public Boolean getNoLaneSplitting() {
        return noLaneSplitting;
    }

    public void setNoLaneSplitting(Boolean noLaneSplitting) {
        this.noLaneSplitting = noLaneSplitting;
    }

    @Override
    public String toString() {
        return String.format(
                "BCL2Fastq [outputDir=%s, inputDir=%s, runFolderDir=%s, sampleSheet=%s, intensitiesDir=%s, interopDir=%s, statsDir=%s, reportsDir=%s, loadingThreads=%s, processingThreads=%s, writingThreads=%s, tiles=%s, minimumTrimmedReadLength=%s, usesBasesMask=%s, maskShortAdapterReads=%s, adapterStringency=%s, ignoreMissingBCLs=%s, ignoreMissingFilter=%s, ignoreMissingPositions=%s, ignoreMissingControls=%s, writeFastqReverseComplement=%s, withFailedReads=%s, createFastqForIndexReads=%s, findAdaptersWithSlidingWindow=%s, noBGZFCompression=%s, fastqCompressionLevel=%s, barcodeMismatches=%s, noLaneSplitting=%s]",
                outputDir, inputDir, runFolderDir, sampleSheet, intensitiesDir, interopDir, statsDir, reportsDir,
                loadingThreads, processingThreads, writingThreads, tiles, minimumTrimmedReadLength, usesBasesMask,
                maskShortAdapterReads, adapterStringency, ignoreMissingBCLs, ignoreMissingFilter,
                ignoreMissingPositions, ignoreMissingControls, writeFastqReverseComplement, withFailedReads,
                createFastqForIndexReads, findAdaptersWithSlidingWindow, noBGZFCompression, fastqCompressionLevel,
                barcodeMismatches, noLaneSplitting);
    }

    public static void main(String[] args) {
        BCL2Fastq module = new BCL2Fastq();
        module.setWorkflowName("TEST");

        module.setInputDir(new File("inputdir"));
        module.setBarcodeMismatches(0);
        module.setIgnoreMissingBCLs(Boolean.TRUE);
        module.setWithFailedReads(Boolean.TRUE);
        module.setUsesBasesMask("Y*,I7,Y*");
        module.setTiles("s_1_*");
        module.setOutputDir(new File("outputdir"));
        module.setSampleSheet(new File("/tmp", "samplesheet.csv"));

        try {
            module.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
