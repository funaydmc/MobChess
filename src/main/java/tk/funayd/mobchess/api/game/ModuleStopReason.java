package tk.funayd.mobchess.api.game;

public enum ModuleStopReason {
    /** Hoàn thành nhiệm vụ một cách tự nhiên (Hết giờ, hết quái, thắng cuộc) */
    COMPLETED,

    /** Bị ép buộc dừng (Admin dùng lệnh, server reload, người chơi thoát) */
    CANCELLED,

    /** Dừng do lỗi hệ thống (Exception, config sai) */
    ERROR
}
