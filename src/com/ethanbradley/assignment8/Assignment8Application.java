package com.ethanbradley.assignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Assignment8Application {

	public static void main(String[] args) {
		Assignment8 assignment8 = new Assignment8();
		List<Integer> allNumbers = Collections.synchronizedList(new ArrayList<>());
		ExecutorService executor = Executors.newCachedThreadPool();

		List<CompletableFuture<Void>> tasks = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> assignment8.getNumbers(), executor)
					.thenAccept(numbers -> allNumbers.addAll(numbers));
			tasks.add(task);
		}
		while (tasks.stream().filter(e -> e.isDone()).count() < 1000) {
		}

		System.out.println("The number of records fetched is: " + allNumbers.size());
		
		Map<Integer, Integer> countOfIntegers = allNumbers.stream()
				.collect(Collectors.toMap(key -> key, key -> 1, (oldKey, newKey) -> oldKey + 1));
		
		System.out.println(countOfIntegers);
	}

}
