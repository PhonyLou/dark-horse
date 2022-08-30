package com.tw.darkhorse.domain;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

public class PricePlanTest {

    private final String ENERGY_SUPPLIER_NAME = "Energy Supplier Name";

    @Test
    public void shouldReturnTheEnergySupplierGivenInTheConstructor() {
        PricePlan p = mock(PricePlan.class);
        PricePlan pricePlan = new PricePlan(null, ENERGY_SUPPLIER_NAME, null, null);

        assertThat(pricePlan.getEnergySupplier()).isEqualTo(ENERGY_SUPPLIER_NAME);
    }

    @Test
    public void shouldReturnTheBasePriceGivenAnOrdinaryDateTime() throws Exception {
        LocalDateTime normalDateTime = LocalDateTime.of(2017, Month.AUGUST, 31, 12, 0, 0);
        PricePlan.PeakTimeMultiplier peakTimeMultiplier = new PricePlan.PeakTimeMultiplier(DayOfWeek.WEDNESDAY, BigDecimal.TEN);
        PricePlan pricePlan = new PricePlan(null, null, BigDecimal.ONE, singletonList(peakTimeMultiplier));

        BigDecimal price = pricePlan.getPrice(normalDateTime);

        assertThat(price).isCloseTo(BigDecimal.ONE, Percentage.withPercentage(1));
    }

    @Test
    public void shouldReturnAnExceptionPriceGivenExceptionalDateTime() throws Exception {
        LocalDateTime exceptionalDateTime = LocalDateTime.of(2017, Month.AUGUST, 30, 23, 0, 0);
        PricePlan.PeakTimeMultiplier peakTimeMultiplier = new PricePlan.PeakTimeMultiplier(DayOfWeek.WEDNESDAY, BigDecimal.TEN);
        PricePlan pricePlan = new PricePlan(null, null, BigDecimal.ONE, singletonList(peakTimeMultiplier));

        BigDecimal price = pricePlan.getPrice(exceptionalDateTime);

        assertThat(price).isCloseTo(BigDecimal.TEN, Percentage.withPercentage(1));
    }

    @Test
    public void shouldReceiveMultipleExceptionalDateTimes() throws Exception {
        LocalDateTime exceptionalDateTime = LocalDateTime.of(2017, Month.AUGUST, 30, 23, 0, 0);
        PricePlan.PeakTimeMultiplier peakTimeMultiplier = new PricePlan.PeakTimeMultiplier(DayOfWeek.WEDNESDAY, BigDecimal.TEN);
        PricePlan.PeakTimeMultiplier otherPeakTimeMultiplier = new PricePlan.PeakTimeMultiplier(DayOfWeek.TUESDAY, BigDecimal.TEN);
        List<PricePlan.PeakTimeMultiplier> peakTimeMultipliers = Arrays.asList(peakTimeMultiplier, otherPeakTimeMultiplier);
        PricePlan pricePlan = new PricePlan(null, null, BigDecimal.ONE, peakTimeMultipliers);

        BigDecimal price = pricePlan.getPrice(exceptionalDateTime);

        assertThat(price).isCloseTo(BigDecimal.TEN, Percentage.withPercentage(1));
    }

    @Test
    public void xxx() {
        LocalDate date = LocalDate.now();
        LocalDate startOfPreviousWeek = date.plusWeeks(-1).with(DayOfWeek.MONDAY);
        LocalDate endOfPreviousWeek = date.plusWeeks(-1).with(DayOfWeek.SUNDAY);
        System.out.println(date);
        System.out.println(startOfPreviousWeek);
        System.out.println(endOfPreviousWeek);
    }

    @Test
    public void abc() {
        List<String> l = Arrays.asList("a", "b", "c", "a", "c", "d");
        Map<Object, Long> a = l.stream().map(s -> singletonMap(s, 1)).collect(Collectors.groupingBy(Map::keySet, Collectors.counting()));
        System.out.println(a);

        Map<String, Long> countingMap = l.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        System.out.println(countingMap);

        HashMap<String, Long> collect = countingMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(
                e -> e.getKey(), e -> e.getValue(), (d, dd) -> -1L, LinkedHashMap::new)
        );
        System.out.println("==========");
        System.out.println(collect);
        System.out.println("==========");

//        R collect1 = l.stream().collect(Collectors.toMap(String::toString, String::toString, LinkedHashMap.class));
    }
}
