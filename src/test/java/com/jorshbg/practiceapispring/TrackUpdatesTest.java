package com.jorshbg.practiceapispring;

import com.jorshbg.practiceapispring.util.UpdatesTrackerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;

@SpringBootTest
public class TrackUpdatesTest {

    @Test
    void testTrackUpdateOfSingleClass() throws IllegalAccessException {
        var previous = new Permission(1, "blog", "write");
        var updated = new Permission(2, "blog", "read");

        var tracker = new UpdatesTrackerUtil<Permission>();
        var result = tracker.getUpdates(previous, updated);
        Assertions.assertNotNull(result);
        for (String key : result.keySet()) {
            for (String subKey : result.get(key).keySet()) {
                Assertions.assertNotNull(subKey);
                Assertions.assertNotEquals(subKey, result.get(key).get(subKey));
            }
        }
    }

    @Test
    void testTrackUpdateOfTwoClasses() throws IllegalAccessException {
        var previousPermission1 = new Permission(1, "blog", "write");
        var previousPermission2 = new Permission(2, "blog", "read");
        var previousPermission3 = new Permission(3, "comment", "read");
        var updatedPermission1 = new Permission(5, "post", "read");
        var updatedPermission2 = new Permission(2, "comment", "read");
        var updatedPermission3 = new Permission(4, "blog", "write");
        var previousRole = new Role(1, "Administrator", new Permission[]{previousPermission1, previousPermission2, previousPermission3});
        var updatedRole = new Role(2, "User", new Permission[]{updatedPermission1, updatedPermission2, updatedPermission3});

        var tracker = new UpdatesTrackerUtil<Role>();
        var result = tracker.getUpdates(previousRole, updatedRole);
        Assertions.assertNotNull(result);
        System.err.println(result);
    }

    @Test
    void testTrackUpdatedOfMultipleClases() throws IllegalAccessException {
        var previousPermission1 = new Permission(1, "blog", "write");
        var previousPermission2 = new Permission(2, "blog", "read");
        var previousPermission3 = new Permission(3, "comment", "read");
        var updatedPermission1 = new Permission(5, "post", "read");
        var updatedPermission2 = new Permission(2, "comment", "read");
        var updatedPermission3 = new Permission(4, "blog", "write");
        var previousRole = new Role(1, "Administrator", new Permission[]{previousPermission1, previousPermission2, previousPermission3});
        var updatedRole = new Role(2, "User", new Permission[]{updatedPermission1, updatedPermission2, updatedPermission3});
        var userPrevious = new User(1, "jordi", 23.4d, previousRole);
        var userUpdated = new User(2, "Jordi", 2300.4d, updatedRole);


        var tracker = new UpdatesTrackerUtil<User>();
        var result = tracker.getUpdates(userPrevious, userUpdated);
        Assertions.assertNotNull(result);
        System.err.println(result);
    }

    @Test
    void testTrackUpdatedOfClassWithMapProperties() throws IllegalAccessException {
        var user1 = new User(2, "Jordsh", 34232.4, null);
        var user2 = new User(5, "Alucard", 34.34, null);
        var user3 = new User(6, "Garcia", 32443.34534, null);
        var user4 = new User(9, "Yair", 3420.4, null);
        var metadata = new LinkedHashMap<String, String>();
        metadata.put("one", "two");
        metadata.put("three", "four");
        var links = new LinkedHashMap<String, String>();
        links.put("1", "palabra");
        var response = new ApiResponse<>(3432492349L, new User[]{user1, user2, user3, user4}, metadata, links);
        var metadata2 = new LinkedHashMap<String, String>();
        metadata2.put("five", "six");
        var links2 = new LinkedHashMap<String, String>();
        links2.put("2", "cuatro");
        links2.put("4", "algo");
        var responseUpdated = new ApiResponse<>(3432449L, new User[]{user1, user2, user3, user4}, metadata2, links2);
        var tracker = new UpdatesTrackerUtil<ApiResponse<User>>();
        var result = tracker.getUpdates(response, responseUpdated);
        Assertions.assertNotNull(result);
        System.err.println(result);
    }

    private class ApiResponse<T> {
        private Long version = -234234998L;
        private T[] data;
        private LinkedHashMap<String, String> metadata;
        private LinkedHashMap<String, String> links;

        public ApiResponse(Long version, T[] data, LinkedHashMap<String, String> metadata, LinkedHashMap<String, String> links) {
            this.version = version;
            this.data = data;
            this.metadata = metadata;
            this.links = links;
        }
    }


    private class User {
        private int id;
        private String name;
        private double salary;
        private Role role;

        public User(int id, String name, double salary, Role role) {
            this.id = id;
            this.name = name;
            this.salary = salary;
            this.role = role;
        }
    }

    private class Role {
        private int id;
        private String name;
        private Permission[] permissions;

        public Role(int id, String name, Permission[] permissions) {
            this.id = id;
            this.name = name;
            this.permissions = permissions;
        }
    }

    private class Permission {
        private int id;
        private String name;
        private String type;

        public Permission(int id, String name, String type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            return String.format("Permission [id=%d, name=%s, type=%s]", id, name, type);
        }
    }

}
