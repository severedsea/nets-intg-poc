package com.singaporepower.poc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class NetsKeyGeneratorTest {

	private NetsKeyGenerator service;

	@Before
	public void onSetUp() {
		service = new NetsKeyGenerator();
	}

	@Test
	public void testCopyFromClasspath() {
		// Given
		File file = null;
		String source = "keys/merchant-priv.pgp.asc";
		try {
			file = ResourceUtils.getFile("classpath:" + source);
		}
		catch (FileNotFoundException e) {
			fail("Test file is missing.");
		}
		assertThat(file).isNotNull();

		// When
		File generated = null;
		try {
			generated = service.copyFromClasspath(source);
		}
		catch(IOException e) {
			fail(e.getMessage());
		}

		// Then
		assertThat(generated).isNotNull();
		try {
			assertThat(Files.readAllBytes(file.toPath())).isEqualTo(Files.readAllBytes(generated.toPath()));
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = IOException.class)
	public void testCopyFromClasspath_MissingSource() throws IOException {
		// Given
		String source = "missing/source.pgp.asc";

		// When
		File generated = service.copyFromClasspath(source);

		// Then
		assertThat(generated).isNull();
	}

}
