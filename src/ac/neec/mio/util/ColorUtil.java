package ac.neec.mio.util;

public class ColorUtil {
	
	public static final String DEFAULT_COLOR = "#000000";
	
	private static final String[] codes = { "#000000", "#008b8b", "#ffffe0",
			"#ff4500", "#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500", "#191970",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#ffa500", "#8b4513", "#000000", "#008b8b", "#ffffe0", "#ff4500",
			"#191970", "#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513",
			"#8a2be2", "#5f9ea0", "#9acd32", "#ff1493", "#ffa500", "#8b4513" };

	public static String getColor(int position) {
		return codes[position];
	}

}
