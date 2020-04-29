public interface DBFilter {
    public RMSValues[] getRms();
    public void setRms(RMSValues[] rms);

    public DBValues getDB();
    public void setDB(DBValues DB);

    public void calculate();
    public void showGraph(int series);
}
