install:
	docker compose up -d --build

clean:
	docker exec -it app ./gradlew clean

run:
	docker exec -it app ./gradlew bootRun

test:
	docker exec -it app ./gradlew test

uninstall:
	docker compose down && docker compose rm