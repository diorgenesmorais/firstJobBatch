package com.dms.firstJobBatch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.dms.firstJobBatch.model.Pessoa;

@Configuration
public class ReaderConfig {
	@Bean
	ItemReader<Pessoa> reader() {
		return new FlatFileItemReaderBuilder<Pessoa>()
				.name("reader")
				.resource(new FileSystemResource("files/pessoas.csv"))
				.comments("--")
				.delimited()
				.names("nome", "email", "dataNascimento", "idade", "id")
				.targetType(Pessoa.class)
				.build();
	}
}
