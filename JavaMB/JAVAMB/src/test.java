package PhanQuyenACL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Định nghĩa các loại quyền truy cập
enum Permission {
    READ,
    WRITE,
    DELETE
}

// Lớp User đại diện cho mỗi người dùng trong ứng dụng
class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

// Lớp đại diện cho Access Control List (ACL) của ứng dụng
class AccessControlList {
    private Map<String, Map<String, Set<Permission>>> acl; // Key: Resource, Value: Map<Key: User/Group, Value: Set of
                                                           // Permissions>

    public AccessControlList() {
        this.acl = new HashMap<>();
    }

    // Thêm quyền truy cập cho người dùng hoặc nhóm người dùng vào tài nguyên cụ thể
    public void addPermission(String resource, String userOrGroup, Permission permission) {
        acl.putIfAbsent(resource, new HashMap<>());
        acl.get(resource).putIfAbsent(userOrGroup, new HashSet<>());
        acl.get(resource).get(userOrGroup).add(permission);
    }

    // Kiểm tra quyền truy cập của người dùng đối với tài nguyên cụ thể
    public boolean hasPermission(String resource, String user, Permission permission) {
        Map<String, Set<Permission>> resourcePermissions = acl.get(resource);
        if (resourcePermissions == null) {
            return false;
        }

        Set<Permission> userPermissions = resourcePermissions.get(user);
        if (userPermissions == null) {
            return false;
        }

        return userPermissions.contains(permission);
    }
}

// Lớp đại diện cho ứng dụng Java của bạn
public class test {
    public static void main(String[] args) {
        // Tạo ACL và thêm quyền truy cập cho tài nguyên cụ thể
        AccessControlList acl = new AccessControlList();
        acl.addPermission("file.txt", "user1", Permission.READ);
        acl.addPermission("file.txt", "adminGroup", Permission.READ);
        acl.addPermission("file.txt", "adminGroup", Permission.WRITE);
        acl.addPermission("data.db", "user2", Permission.READ);
        acl.addPermission("data.db", "user2", Permission.WRITE);

        // Kiểm tra quyền truy cập của người dùng đối với các tài nguyên
        String user = "user1";
        String resource = "file.txt";

        if (acl.hasPermission(resource, user, Permission.READ)) {
            System.out.println("Người dùng " + user + " được cấp quyền đọc tài nguyên " + resource);
        } else {
            System.out.println("Người dùng " + user + " không được cấp quyền đọc tài nguyên " + resource);
        }
    }
}
