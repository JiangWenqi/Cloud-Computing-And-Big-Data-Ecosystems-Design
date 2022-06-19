package es.upm.fi.cloud.YellowTaxiTrip.models;

import java.sql.Timestamp;

/**
 * @author wenqi jiang
 */
public class TaxiReport {
    /**
     * VendorID
     * A code indicating the TPEP provider that provided the record.
     * 1 = Creative Mobile Technologies, LLC; 2 = VeriFone Inc...
     */
    private Integer vendorId;
    /**
     * tpep_pickup_datetime: The date and time when the meter was engaged.
     */
    private Timestamp tpepPickupDatetime;
    /**
     * tpep_dropoff_datetime: The date and time when the meter was disengaged.
     */
    private Timestamp tpepDropoffDatetime;
    /**
     * passenger_count: The number of passengers in the vehicle. This is a driver-entered value.
     */
    private Double passengerCount;
    /**
     * trip_distance: The elapsed trip distance in miles reported by the taximeter.
     */
    private Double tripDistance;
    /**
     * RatecodeID:
     * The final rate code in effect at the end of the trip.
     * 1 = Standard rate; 2 = JFK; 3 = Newark; 4 = Nassau or Westchester; 5 = Negotiated fare; 6 = Group ride.
     */
    private Double rateCodeId;
    /**
     * store_and_fwd_flag:
     * This flag indicates whether the trip record was held in the vehicle memory before sending it to the vendor,
     * aka “store and forward”, because the vehicle did not have connection to the server.
     * Y= store and forward trip; N= not a store and forward trip.
     */
    private Boolean storeAndFwdFlag;
    /**
     * PULocationID: TLC Taxi Zone in which the taximeter was engaged.
     */
    private Integer puLocationId;
    /**
     * DOLocationID: TLC Taxi Zone in which the taximeter was disengaged.
     */
    private Integer doLocationId;


    /**
     * payment_type:
     * A numeric code signifying how the passenger paid for the trip.
     * 1 = Credit card; 2 = Cash; 3 = No charge; 4 = Dispute; 5 = Unknown; 6 = Voided trip.
     */

    private Integer paymentType;
    /**
     * fare_amount: The time-and-distance fare calculated by the meter.
     */
    private Double fareAmount;


    /**
     * Extra: Miscellaneous extras and surcharges.
     * Currently, this only includes the $0.50 and $1 rush hour and overnight charges.
     */
    private Double extra;
    /**
     * mta_tax: $0.50 MTA tax that is automatically triggered based on the metered rate in use.
     */
    private Double mtaTax;
    /**
     * tip_amount: Tip amount – _This field is automatically populated for credit card tips. Cash tips are not included.
     */
    private Double tipAmount;
    /**
     * tolls_amount: Total amount of all tolls paid in trip.
     */
    private Double tollsAmount;
    /**
     * improvement_surcharge: $0.30 improvement surcharge assessed trips at the flag drop. The improvement surcharge began being levied in 2015.
     */
    private Double improvementSurcharge;
    /**
     * total_amount: The total amount charged to passengers. It does not include cash tips.
     */
    private Double totalAmount;
    /**
     * congestion_surcharge: The surcharge applied when the trip goes through a congested area.
     */
    private Double congestionSurcharge;
    /**
     * airport_fee: The fee applied to the trip when it starts or ends at an airport.
     */
    private Double airportFee;

    public TaxiReport() {
        vendorId = 0;
        tpepPickupDatetime = new Timestamp(0);
        tpepDropoffDatetime = new Timestamp(0);
        passengerCount = 0.0;
        tripDistance = 0.0;
        rateCodeId = 0.0;
        storeAndFwdFlag = false;
        puLocationId = 0;
        doLocationId = 0;
        paymentType = 0;
        fareAmount = 0.0;
        extra = 0.0;
        mtaTax = 0.0;
        tipAmount = 0.0;
        tollsAmount = 0.0;
        improvementSurcharge = 0.0;
        totalAmount = 0.0;
        congestionSurcharge = 0.0;
        airportFee = 0.0;
    }

    public TaxiReport(Integer vendorId, Timestamp tpepPickupDatetime, Timestamp tpepDropoffDatetime, Double passengerCount, Double tripDistance, Double rateCodeId, Boolean storeAndFwdFlag, Integer puLocationId, Integer doLocationId, Integer paymentType, Double fareAmount, Double extra, Double mtaTax, Double tipAmount, Double tollsAmount, Double improvementSurcharge, Double totalAmount, Double congestionSurcharge, Double airportFee) {
        this.vendorId = vendorId;
        this.tpepPickupDatetime = tpepPickupDatetime;
        this.tpepDropoffDatetime = tpepDropoffDatetime;
        this.passengerCount = passengerCount;
        this.tripDistance = tripDistance;
        this.rateCodeId = rateCodeId;
        this.storeAndFwdFlag = storeAndFwdFlag;
        this.puLocationId = puLocationId;
        this.doLocationId = doLocationId;
        this.paymentType = paymentType;
        this.fareAmount = fareAmount;
        this.extra = extra;
        this.mtaTax = mtaTax;
        this.tipAmount = tipAmount;
        this.tollsAmount = tollsAmount;
        this.improvementSurcharge = improvementSurcharge;
        this.totalAmount = totalAmount;
        this.congestionSurcharge = congestionSurcharge;
        this.airportFee = airportFee;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Timestamp getTpepPickupDatetime() {
        return tpepPickupDatetime;
    }

    public void setTpepPickupDatetime(Timestamp tpepPickupDatetime) {
        this.tpepPickupDatetime = tpepPickupDatetime;
    }

    public Timestamp getTpepDropoffDatetime() {
        return tpepDropoffDatetime;
    }

    public void setTpepDropoffDatetime(Timestamp tpepDropoffDatetime) {
        this.tpepDropoffDatetime = tpepDropoffDatetime;
    }

    public Double getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Double passengerCount) {
        this.passengerCount = passengerCount;
    }

    public Double getTripDistance() {
        return tripDistance;
    }

    public void setTripDistance(Double tripDistance) {
        this.tripDistance = tripDistance;
    }

    public Double getRateCodeId() {
        return rateCodeId;
    }

    public void setRateCodeId(Double rateCodeId) {
        this.rateCodeId = rateCodeId;
    }

    public Boolean getStoreAndFwdFlag() {
        return storeAndFwdFlag;
    }

    public void setStoreAndFwdFlag(Boolean storeAndFwdFlag) {
        this.storeAndFwdFlag = storeAndFwdFlag;
    }

    public Integer getPuLocationId() {
        return puLocationId;
    }

    public void setPuLocationId(Integer puLocationId) {
        this.puLocationId = puLocationId;
    }

    public Integer getDoLocationId() {
        return doLocationId;
    }

    public void setDoLocationId(Integer doLocationId) {
        this.doLocationId = doLocationId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Double getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(Double fareAmount) {
        this.fareAmount = fareAmount;
    }

    public Double getExtra() {
        return extra;
    }

    public void setExtra(Double extra) {
        this.extra = extra;
    }

    public Double getMtaTax() {
        return mtaTax;
    }

    public void setMtaTax(Double mtaTax) {
        this.mtaTax = mtaTax;
    }

    public Double getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(Double tipAmount) {
        this.tipAmount = tipAmount;
    }

    public Double getTollsAmount() {
        return tollsAmount;
    }

    public void setTollsAmount(Double tollsAmount) {
        this.tollsAmount = tollsAmount;
    }

    public Double getImprovementSurcharge() {
        return improvementSurcharge;
    }

    public void setImprovementSurcharge(Double improvementSurcharge) {
        this.improvementSurcharge = improvementSurcharge;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getCongestionSurcharge() {
        return congestionSurcharge;
    }

    public void setCongestionSurcharge(Double congestionSurcharge) {
        this.congestionSurcharge = congestionSurcharge;
    }

    public Double getAirportFee() {
        return airportFee;
    }

    public void setAirportFee(Double airportFee) {
        this.airportFee = airportFee;
    }

    @Override
    public String toString() {
        return "TaxiReport{" +
                "vendorId=" + vendorId +
                ", tpepPickupDatetime=" + tpepPickupDatetime +
                ", tpepDropoffDatetime=" + tpepDropoffDatetime +
                ", passengerCount=" + passengerCount +
                ", tripDistance=" + tripDistance +
                ", ratecodeId=" + rateCodeId +
                ", storeAndFwdFlag=" + storeAndFwdFlag +
                ", puLocationId=" + puLocationId +
                ", doLocationId=" + doLocationId +
                ", paymentType=" + paymentType +
                ", fareAmount=" + fareAmount +
                ", extra=" + extra +
                ", mtaTax=" + mtaTax +
                ", tipAmount=" + tipAmount +
                ", tollsAmount=" + tollsAmount +
                ", improvementSurcharge=" + improvementSurcharge +
                ", totalAmount=" + totalAmount +
                ", congestionSurcharge=" + congestionSurcharge +
                ", airportFee=" + airportFee +
                '}';
    }
}
