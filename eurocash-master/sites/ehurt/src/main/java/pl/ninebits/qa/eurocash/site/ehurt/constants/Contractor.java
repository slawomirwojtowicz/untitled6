package pl.ninebits.qa.eurocash.site.ehurt.constants;

public enum Contractor {

  LEWIATAN("DELIKATESY LEWIATAN", "70000945", "8392864052"),
  DOMWESELNY("DOM WESELNY W STARYM MŁYNIE - TEST", "70109450", "5321150042"),
  INMEDIO("HDS INMEDIO 31907", "70000287", "5262100142"),
  DJN("DJN","70317017","7962969423"),
  ABC("KORONA ZOFIA - ABC PLUS", "70003170","5711250857");

  private String contractorName;
  private String contractorId;
  private String nip;

  Contractor(String contractorName, String contractorId, String nip) {
    this.contractorName = contractorName;
    this.contractorId = contractorId;
    this.nip = nip;
  }

  public String getContractorName() {
    return contractorName;
  }

  public String getContractorId() {
    return contractorId;
  }

  public String getNip() {
    return nip;
  }
}
