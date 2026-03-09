package com.rmkit.example.demo.model;

import java.util.List;

public class CourseInfo {
    private final String programTitle;
    private final int intake;
    private final String accreditationNote;
    private final List<String> honoursSpecializations;
    private final List<String> minorOptions;
    private final List<String> highlightBullets;
    private final String sourceUrl;

    public CourseInfo(
            String programTitle,
            int intake,
            String accreditationNote,
            List<String> honoursSpecializations,
            List<String> minorOptions,
            List<String> highlightBullets,
            String sourceUrl) {
        this.programTitle = programTitle;
        this.intake = intake;
        this.accreditationNote = accreditationNote;
        this.honoursSpecializations = honoursSpecializations;
        this.minorOptions = minorOptions;
        this.highlightBullets = highlightBullets;
        this.sourceUrl = sourceUrl;
    }

    public String getProgramTitle() { return programTitle; }
    public int getIntake() { return intake; }
    public String getAccreditationNote() { return accreditationNote; }
    public List<String> getHonoursSpecializations() { return honoursSpecializations; }
    public List<String> getMinorOptions() { return minorOptions; }
    public List<String> getHighlightBullets() { return highlightBullets; }
    public String getSourceUrl() { return sourceUrl; }
}