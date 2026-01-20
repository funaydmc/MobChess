package tk.funayd.mobchess.api.game;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PhaseMetadata {
    @Builder.Default String name = "unnamed"; // Tên hiển thị của phase
    @Builder.Default long timeout = 0; // Thời gian tối đa của phase (ticks). 0 = vô hạn.
}
