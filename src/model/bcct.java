package model;

public class bcct {
    private int Nam;
    private int Thang;
    private double ChiPhi;
    private double DoanhThu;
    private double LoiNhuan;
    
    public bcct() {
    
    }

    public bcct(int nam, int thang, double chiPhi, double doanhThu, double loiNhuan) {
        this.Nam = nam;
        this.Thang = thang;
        this.ChiPhi = chiPhi;
        this.DoanhThu = doanhThu;
        this.LoiNhuan = loiNhuan;
    }

    public int getNam() {
        return Nam;
    }

    public void setNam(int nam) {
        Nam = nam;
    }

    public int getThang() {
        return Thang;
    }

    public void setThang(int thang) {
        Thang = thang;
    }

    public double getChiPhi() {
        return ChiPhi;
    }

    public void setChiPhi(double chiPhi) {
        ChiPhi = chiPhi;
    }

    public double getDoanhThu() {
        return DoanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        DoanhThu = doanhThu;
    }

    public double getLoiNhuan() {
        return LoiNhuan;
    }

    public void setLoiNhuan(double loiNhuan) {
        LoiNhuan = loiNhuan;
    }


}
