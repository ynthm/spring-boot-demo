package com.ynthm.demo.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.util.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class JsonFileArgumentsProvider
    implements ArgumentsProvider, AnnotationConsumer<JsonFileSource> {
  private final BiFunction<Class<?>, String, InputStream> inputStreamProvider;
  private JsonFileSource annotation;
  private String[] resources;
  private String[] paths;
  private Class<?> clazz;
  private ObjectMapper objectMapper;

  public JsonFileArgumentsProvider() {
    this(Class::getResourceAsStream);
  }

  public JsonFileArgumentsProvider(BiFunction<Class<?>, String, InputStream> inputStreamProvider) {
    this.inputStreamProvider = inputStreamProvider;
  }

  @Override
  public void accept(JsonFileSource annotation) {
    this.annotation = annotation;
    this.resources = annotation.resources();
    this.paths = annotation.paths();
    this.clazz = annotation.clazz();
    this.objectMapper = new ObjectMapper();
  }

  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Arrays.stream(this.resources)
        .map(
            (resource) -> {
              return this.openInputStream(context, resource);
            })
        .flatMap(this::toStream);
  }

  private InputStream openInputStream(ExtensionContext context, String resource) {
    Preconditions.notBlank(
        resource, "Classpath resource [" + resource + "] must not be null or blank");
    Class<?> testClass = context.getRequiredTestClass();
    return Preconditions.notNull(
        this.inputStreamProvider.apply(testClass, resource),
        () -> "Classpath resource [" + resource + "] does not exist");
  }

  private Stream<Arguments> toStream(InputStream inputStream) {
    try {
      JsonNode rootNode = objectMapper.readTree(inputStream);
      if (paths.length < 1) {
        if (rootNode.isArray()) {
          return ((List)
                  objectMapper.readValue(
                      rootNode.traverse(),
                      objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)))
              .stream().map(i -> Arguments.of(i));
        } else {
          return Stream.of(Arguments.arguments(objectMapper.readValue(rootNode.traverse(), clazz)));
        }
      } else {
        for (String path : paths) {
          rootNode = rootNode.path(path);
        }
        // Spliterator 转 Stream
        if (rootNode.isArray()) {
          return StreamSupport.stream(
                  Spliterators.spliteratorUnknownSize(rootNode.elements(), Spliterator.ORDERED),
                  false)
              .map(
                  i -> {
                    try {
                      return Arguments.arguments(objectMapper.readValue(i.traverse(), clazz));
                    } catch (IOException e) {
                      throw new RuntimeException(e);
                    }
                  });
        }
        return Stream.of(Arguments.arguments(objectMapper.readValue(rootNode.traverse(), clazz)));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
