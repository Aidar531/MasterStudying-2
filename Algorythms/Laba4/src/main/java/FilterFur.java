public interface FilterFur {
    public SampleValue getSv();
    public void setSv(SampleValue sv);
    public RMSValues getRms();
    public void setRms(RMSValues rms);
    public void calculate();
    public void showGraph(int series);
}
