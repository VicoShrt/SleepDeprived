package me.shortman.sleep_deprived.lib;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Convertor {
    public final int TPS = Constants.TICKS_PER_SECOND;
    public final String DEFAULT_TIME_PATTERN = "%Hh %Mm %Ss";

    public String ticksToTimeStringStripped(int ticks, String pattern) {
        return formatDurationStripped(ticks / TPS, pattern);
    }
    public String ticksToTimeStringStripped(int ticks) {
        return formatDurationStripped(ticks / TPS, DEFAULT_TIME_PATTERN);
    }

    private static String formatDurationStripped(int totalSeconds, String pattern) {
        Duration duration = Duration.ofSeconds(totalSeconds);

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        String result = pattern;

        // Replace patterns with actual values
        if (hours > 0) {
            result = result.replaceAll("%Hh+", hours + "h");
        } else {
            result = result.replaceAll("%Hh+", "");
        }
        if (minutes > 0) {
            result = result.replaceAll("%Mm+", minutes + "m");
        } else {
            result = result.replaceAll("%Mm+", "");
        }
        if (seconds > 0) {
            result = result.replaceAll("%Ss+", seconds + "s");
        } else {
            result = result.replaceAll("%Ss+", "");
        }
        return result.trim();
    }

    // Alternative version that always shows all components (no zero removal)
    public static String formatDurationFull(int totalSeconds, String pattern) {
        Duration duration = Duration.ofSeconds(totalSeconds);

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        String result = pattern;
        result = result.replaceAll("%H+", String.valueOf(hours));
        result = result.replaceAll("%M+", String.valueOf(minutes));
        result = result.replaceAll("%S+", String.valueOf(seconds));

        return result;
    }

    // Version with zero-padding support
    public static String formatDurationPadded(int totalSeconds, String pattern) {
        Duration duration = Duration.ofSeconds(totalSeconds);

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        String result = pattern;

        // Handle zero-padded patterns (%HH, %MM, %SS)
        Pattern hourPattern = Pattern.compile("%H{2,}");
        Matcher hourMatcher = hourPattern.matcher(result);
        if (hourMatcher.find()) {
            int padding = hourMatcher.group().length() - 1; // -1 for the %
            result = hourMatcher.replaceAll(String.format("%0" + padding + "d", hours));
        } else {
            result = result.replaceAll("%H+", String.valueOf(hours));
        }

        Pattern minutePattern = Pattern.compile("%M{2,}");
        Matcher minuteMatcher = minutePattern.matcher(result);
        if (minuteMatcher.find()) {
            int padding = minuteMatcher.group().length() - 1;
            result = minuteMatcher.replaceAll(String.format("%0" + padding + "d", minutes));
        } else {
            result = result.replaceAll("%M+", String.valueOf(minutes));
        }

        Pattern secondPattern = Pattern.compile("%S{2,}");
        Matcher secondMatcher = secondPattern.matcher(result);
        if (secondMatcher.find()) {
            int padding = secondMatcher.group().length() - 1;
            result = secondMatcher.replaceAll(String.format("%0" + padding + "d", seconds));
        } else {
            result = result.replaceAll("%S+", String.valueOf(seconds));
        }

        return result;
    }
}
