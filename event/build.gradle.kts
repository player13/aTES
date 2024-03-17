plugins {
    id("com.github.davidmc24.gradle.plugin.avro")
    id("com.github.imflog.kafka-schema-registry-gradle-plugin")
}

dependencies {
    // serialization
    implementation("org.apache.avro:avro")
}

schemaRegistry {
    url = "http://localhost:9091"
    quiet = false
//	outputDirectory = "build/schema-registry/results"
    pretty = true
    register {
        val avroSrc = "$projectDir/src/main/avro"

        // user
        subject("com.github.player13.ates.event.user.UserCreated", "$avroSrc/user/UserCreated.avsc", "AVRO")
            .addLocalReference("Role", "$avroSrc/user/Role.avsc")

        // task
        subject("com.github.player13.ates.event.task.TaskCreated", "$avroSrc/task/TaskCreated.avsc", "AVRO")
            .addLocalReference("Status", "$avroSrc/task/Status.avsc")
        subject("com.github.player13.ates.event.task.TaskUpdated", "$avroSrc/task/TaskUpdated.avsc", "AVRO")
            .addLocalReference("Status", "$avroSrc/task/Status.avsc")

        subject("com.github.player13.ates.event.task.TaskAdded", "$avroSrc/task/TaskAdded.avsc", "AVRO")
        subject("com.github.player13.ates.event.task.TaskAssigned", "$avroSrc/task/TaskAssigned.avsc", "AVRO")
        subject("com.github.player13.ates.event.task.TaskCompleted", "$avroSrc/task/TaskCompleted.avsc", "AVRO")

        // transaction
        subject("com.github.player13.ates.event.transaction.TransactionApplied", "$avroSrc/transaction/TransactionApplied.avsc", "AVRO")
    }
}