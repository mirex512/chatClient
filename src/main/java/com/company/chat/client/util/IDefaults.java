package com.company.chat.client.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

public interface IDefaults {

	public static final Charset defaultCharset = StandardCharsets.UTF_8;
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");

}
