package com.example.cardiacrecorder;

import static com.example.cardiacrecorder.viewmodel.FilterViewModel.HIGH;
import static com.example.cardiacrecorder.viewmodel.FilterViewModel.LOW;
import static com.example.cardiacrecorder.viewmodel.FilterViewModel.NORMAL;
import static org.junit.Assert.assertTrue;

import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import com.example.cardiacrecorder.classes.EachData;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EachDataTest {
    @Test
    public void testGetEpochDate() {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US));
        long expectedEpochDate = LocalDate.now().toEpochDay();

        long epochDate = EachData.getEpochDate(currentDate);

        Assert.assertEquals(expectedEpochDate, epochDate);
    }

    @Test
    public void testGetEpochDate_InvalidDate() {
        String invalidDate = "Invalid Date";
        long expectedEpochDate = Long.MIN_VALUE;

        long epochDate = EachData.getEpochDate(invalidDate);

        Assert.assertEquals(expectedEpochDate, epochDate);
    }

    @Test
    public void testIsSysUnusual() {
        int id = 1;
        long timestamp = System.currentTimeMillis();
        String date = "01-01-2023";
        String time = "12:00AM";
        int sysPressure = 120;
        int dysPressure = 80;
        int heartRate = 70;
        String comment = "Sample comment";

        EachData data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, heartRate, comment);

        Assert.assertFalse("Failed for sysPressure = 120", data.isSysUnusual());

        data = new EachData(id, timestamp, date, time, 80, dysPressure, heartRate, comment);
        Assert.assertTrue("Failed for sysPressure = 80", data.isSysUnusual());

        data = new EachData(id, timestamp, date, time, 140, dysPressure, heartRate, comment);
        Assert.assertFalse("Failed for sysPressure = 140", data.isSysUnusual());

        data = new EachData(id, timestamp, date, time, 89, dysPressure, heartRate, comment);
        Assert.assertTrue("Failed for sysPressure = 89", data.isSysUnusual());

        data = new EachData(id, timestamp, date, time, 141, dysPressure, heartRate, comment);
        Assert.assertTrue("Failed for sysPressure = 141", data.isSysUnusual());
    }

    @Test
    public void testGetSysStatus() {
        int id = 1;
        long timestamp = System.currentTimeMillis();
        String date = "01-01-2023";
        String time = "12:00AM";
        int sysPressure = 120;
        int dysPressure = 80;
        int heartRate = 70;
        String comment = "Sample comment";

        EachData data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, heartRate, comment);

        Assert.assertEquals("ok", data.getSysStatus());

        data = new EachData(id, timestamp, date, time, 80, dysPressure, heartRate, comment);
        Assert.assertEquals("low", data.getSysStatus());

        data = new EachData(id, timestamp, date, time, 141, dysPressure, heartRate, comment);
        Assert.assertEquals("high", data.getSysStatus());
    }

    @Test
    public void testGetHeartRateStatus() {
        int id = 1;
        long timestamp = System.currentTimeMillis();
        String date = "01-01-2023";
        String time = "12:00AM";
        int sysPressure = 120;
        int dysPressure = 80;
        int heartRate = 70;
        String comment = "Sample comment";

        EachData data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, heartRate, comment);

        Assert.assertEquals("ok", data.getHeartRateStatus());

        data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, 55, comment);
        Assert.assertEquals("low", data.getHeartRateStatus());

        data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, 105, comment);
        Assert.assertEquals("high", data.getHeartRateStatus());
    }

    @Test
    public void testIsDysUnusual() {
        int id = 1;
        long timestamp = System.currentTimeMillis();
        String date = "01-01-2023";
        String time = "12:00AM";
        int sysPressure = 120;
        int dysPressure = 80;
        int heartRate = 70;
        String comment = "Sample comment";

        EachData data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, heartRate, comment);

        Assert.assertFalse("Failed for dysPressure = 80", data.isDysUnusual());

        data = new EachData(id, timestamp, date, time, sysPressure, 55, heartRate, comment);
        Assert.assertTrue("Failed for dysPressure = 55", data.isDysUnusual());

        data = new EachData(id, timestamp, date, time, sysPressure, 95, heartRate, comment);
        Assert.assertTrue("Failed for dysPressure = 95", data.isDysUnusual());

        data = new EachData(id, timestamp, date, time, sysPressure, 60, heartRate, comment);
        Assert.assertFalse("Failed for dysPressure = 60", data.isDysUnusual());

        data = new EachData(id, timestamp, date, time, sysPressure, 90, heartRate, comment);
        Assert.assertFalse("Failed for dysPressure = 90", data.isDysUnusual());
    }

    @Test
    public void testIsHeartRateUnusual() {
        int id = 1;
        long timestamp = System.currentTimeMillis();
        String date = "01-01-2023";
        String time = "12:00AM";
        int sysPressure = 120;
        int dysPressure = 80;
        int heartRate = 70;
        String comment = "Sample comment";

        EachData data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, heartRate, comment);

        Assert.assertFalse("Failed for heartRate = 70", data.isHeartRateUnusual());

        data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, 55, comment);
        Assert.assertTrue("Failed for heartRate = 55", data.isHeartRateUnusual());

        data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, 105, comment);
        Assert.assertTrue("Failed for heartRate = 105", data.isHeartRateUnusual());

        data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, 60, comment);
        Assert.assertFalse("Failed for heartRate = 60", data.isHeartRateUnusual());

        data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, 100, comment);
        Assert.assertFalse("Failed for heartRate = 100", data.isHeartRateUnusual());
    }

    @Test
    public void testGetDysStatus() {
        int id = 1;
        long timestamp = System.currentTimeMillis();
        String date = "01-01-2023";
        String time = "12:00AM";
        int sysPressure = 120;
        int dysPressure = 80;
        int heartRate = 70;
        String comment = "Sample comment";

        EachData data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, heartRate, comment);

        Assert.assertEquals("ok", data.getDysStatus());

        data = new EachData(id, timestamp, date, time, sysPressure, 55, heartRate, comment);
        Assert.assertEquals("low", data.getDysStatus());

        data = new EachData(id, timestamp, date, time, sysPressure, 95, heartRate, comment);
        Assert.assertEquals("high", data.getDysStatus());
    }

    @Test
    public void testIsIdSame() {
        int id1 = 1;
        int id2 = 2;

        EachData item1 = new EachData(id1, 123456789, "01-01-2023", "12:00AM", 90, 80, 70, "Comment 1");
        EachData item2 = new EachData(id2, 987654321, "01-01-2022", "12:00AM", 120, 80, 70, "Comment 2");

        boolean isSame1 = item1.isIdSame(item1);
        boolean isSame2 = item1.isIdSame(item2);

        Assert.assertTrue(isSame1);
        Assert.assertFalse(isSame2);
    }

    @Test
    public void testIsFullySame() {
        EachData originalData = new EachData(1, 123456789, "01-01-2023", "12:00AM", 120, 80, 70, "Comment");
        EachData sameData = new EachData(1, 123456789, "01-01-2023", "12:00AM", 120, 80, 70, "Comment");
        EachData differentData = new EachData(2, 987654321, "01-01-2022", "12:00AM", 130, 90, 75, "Different Comment");

        boolean isSame1 = originalData.isFullySame(sameData);
        boolean isSame2 = originalData.isFullySame(differentData);

        Assert.assertTrue(isSame1);
        Assert.assertFalse(isSame2);
    }

    @Test
    public void testGetFormattedSysPressure() {
        int id = 1;
        long timestamp = System.currentTimeMillis();
        String date = "01-01-2023";
        String time = "12:00AM";
        int sysPressure = 120;
        int dysPressure = 80;
        int heartRate = 70;
        String comment = "Sample comment";

        EachData data = new EachData(id, timestamp, date, time, sysPressure, dysPressure, heartRate, comment);

        Assert.assertEquals("120mm Hg", data.getFormattedSysPressure());
    }

    @Test
    public void testGetSpannableSys() {
        int sysPressure = 120;

        EachData eachData = new EachData(1, 123456789, "01-01-2022", "12:00AM", sysPressure, 80, 70, "Comment");

        // Mock the SpannableString
        SpannableString spannableStringMock = Mockito.mock(SpannableString.class);

        // Set the mocked SpannableString
        eachData.setSpanSys(spannableStringMock);

        // Mock the behavior of the SpannableString methods
        String expectedText = sysPressure + "\nmm Hg";
        Mockito.when(spannableStringMock.toString()).thenReturn(expectedText);

        AbsoluteSizeSpan[] expectedSpans = new AbsoluteSizeSpan[2];
        Mockito.when(spannableStringMock.getSpans(Mockito.anyInt(), Mockito.anyInt(), Mockito.eq(AbsoluteSizeSpan.class))).thenReturn(expectedSpans);

        // Call the method to be tested
        SpannableString spannableSys = eachData.getSpannableSys();

        Assert.assertEquals(expectedText, spannableSys.toString());

        AbsoluteSizeSpan[] spans = spannableSys.getSpans(0, spannableSys.length(), AbsoluteSizeSpan.class);
        Assert.assertEquals(expectedSpans.length, spans.length);
    }

    @Test
    public void testGetSysBackground() {
        int sysPressure = 120;

        EachData eachData = new EachData(1, 123456789, "01-01-2022", "12:00AM", sysPressure, 80, 70, "Comment");

        int expectedBackground = R.drawable.round_back_normal;
        int actualBackground = eachData.getSysBackground();

        Assert.assertEquals(expectedBackground, actualBackground);
    }

    @Test
    public void testGetDysBackground() {
        int dysPressure = 80;

        EachData eachData = new EachData(1, 123456789, "01-01-2022", "12:00AM", 120, dysPressure, 70, "Comment");

        int expectedBackground = R.drawable.round_back_normal;
        int actualBackground = eachData.getDysBackground();

        Assert.assertEquals(expectedBackground, actualBackground);
    }

    @Test
    public void testGetHeartBackground() {
        int heartRate = 80;

        EachData eachData = new EachData(1, 123456789, "01-01-2022", "12:00AM", 120, 80, heartRate, "Comment");

        int expectedBackground = R.drawable.round_back_normal;
        int actualBackground = eachData.getHeartBackground();

        Assert.assertEquals(expectedBackground, actualBackground);
    }

    @Test
    public void testGetSpannableDys() {
        int dysPressure = 80;

        EachData eachData = new EachData(1, 123456789, "01-01-2022", "12:00AM", 120, dysPressure, 70, "Comment");

        // Mock the SpannableString
        SpannableString spannableStringMock = Mockito.mock(SpannableString.class);

        // Set the mocked SpannableString
        eachData.setSpanDys(spannableStringMock);

        // Mock the behavior of the SpannableString methods
        String expectedText = dysPressure + "\nmm Hg";
        Mockito.when(spannableStringMock.toString()).thenReturn(expectedText);

        AbsoluteSizeSpan[] expectedSpans = new AbsoluteSizeSpan[2];
        Mockito.when(spannableStringMock.getSpans(Mockito.anyInt(), Mockito.anyInt(), Mockito.eq(AbsoluteSizeSpan.class))).thenReturn(expectedSpans);

        // Call the method to be tested
        SpannableString spannableDys = eachData.getSpannableDys();

        // Verify the expected text
        Assert.assertEquals(expectedText, spannableDys.toString());

        // Verify the expected spans
        AbsoluteSizeSpan[] spans = spannableDys.getSpans(0, spannableDys.length(), AbsoluteSizeSpan.class);
        Assert.assertEquals(expectedSpans.length, spans.length);
    }

    @Test
    public void testGetSpannableHeart() {
        int heartRate = 70;

        EachData eachData = new EachData(1, 123456789, "01-01-2022", "12:00AM", 120, 80, heartRate, "Comment");

        // Mock the SpannableString
        SpannableString spannableStringMock = Mockito.mock(SpannableString.class);

        // Mock the behavior of the SpannableString methods
        String expectedText = heartRate + "\nBPM";
        Mockito.when(spannableStringMock.toString()).thenReturn(expectedText);

        AbsoluteSizeSpan[] expectedSpans = new AbsoluteSizeSpan[2];
        Mockito.when(spannableStringMock.getSpans(Mockito.anyInt(), Mockito.anyInt(), Mockito.eq(AbsoluteSizeSpan.class))).thenReturn(expectedSpans);

        // Call the method to be tested
        eachData.setSpanHeart(spannableStringMock);
        SpannableString spannableHeart = eachData.getSpannableHeart();

        // Perform assertions
        Assert.assertEquals(expectedText, spannableHeart.toString());

        AbsoluteSizeSpan[] spans = spannableHeart.getSpans(0, spannableHeart.length(), AbsoluteSizeSpan.class);
        Assert.assertEquals(expectedSpans.length, spans.length);
    }

    @Test
    public void testGetFormattedDysPressure() {
        int dysPressure = 80;

        EachData eachData = new EachData(1, 123456789, "01-01-2022", "12:00AM", 120, dysPressure, 70, "Comment");

        String expectedFormattedDysPressure = dysPressure + "mm Hg";
        String formattedDysPressure = eachData.getFormattedDysPressure();

        Assert.assertEquals(expectedFormattedDysPressure, formattedDysPressure);
    }

    @Test
    public void testGetSpannableDateTime() {
        long timestamp = 123456789;
        String date = "01-01-2022";
        String tAgo = "2 hours ago";

        EachData eachData = new EachData(1, timestamp, date, "12:00AM", 120, 80, 70, "Comment");

        // Mock the SpannableString
        SpannableString spannableStringMock = Mockito.mock(SpannableString.class);

        // Set the mocked SpannableString
        eachData.setSpanDateTime(spannableStringMock);

        // Mock the behavior of the SpannableString methods
        String expectedText = date + "\n" + tAgo;
        Mockito.when(spannableStringMock.toString()).thenReturn(expectedText);

        AbsoluteSizeSpan[] expectedSpans = new AbsoluteSizeSpan[2];
        Mockito.when(spannableStringMock.getSpans(Mockito.anyInt(), Mockito.anyInt(), Mockito.eq(AbsoluteSizeSpan.class))).thenReturn(expectedSpans);

        // Call the method to be tested
        SpannableString spannableDateTime = eachData.getSpannableDateTime();

        Assert.assertEquals(expectedText, spannableDateTime.toString());

        AbsoluteSizeSpan[] spans = spannableDateTime.getSpans(0, spannableDateTime.length(), AbsoluteSizeSpan.class);
        Assert.assertEquals(expectedSpans.length, spans.length);
    }

    @Test
    public void testGetFormattedHeartRate() {
        int heartRate = 80;

        EachData eachData = new EachData(1, 123456789, "01-01-2022", "12:00AM", 120, 70, heartRate, "Comment");

        String expectedText = heartRate + "BPM";
        String formattedHeartRate = eachData.getFormattedHeartRate();

        Assert.assertEquals(expectedText, formattedHeartRate);
    }

    @Test
    public void testGetSafeComment() {
        String expectedText = "This is a comment";

        EachData eachData = new EachData(1, 123456789, "01-01-2022", "12:00AM", 120, 80, 70, expectedText);

        String safeComment = eachData.getSafeComment();

        Assert.assertEquals(expectedText, safeComment);
    }

    @Test
    public void testGetElapsedTime() {
        long startTime = 123456789; // Replace with your desired start time
        long endTime = 987654321; // Replace with your desired end time

        String expectedText = "10d ago";
        String elapsedTime = EachData.getElapsedTime(startTime, endTime);

        Assert.assertEquals(expectedText, elapsedTime);
    }

    @Test
    public void testIsThisOK(){
        EachData eachData = new EachData(System.currentTimeMillis(),"10/10/2023","10:10PM",60,95,75,"nothing");

        assertTrue(eachData.isThisOK(LOW,HIGH,NORMAL));
    }

}
