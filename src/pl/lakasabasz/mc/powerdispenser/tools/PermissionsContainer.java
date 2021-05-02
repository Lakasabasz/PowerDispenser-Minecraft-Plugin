package pl.lakasabasz.mc.powerdispenser.tools;

import java.util.HashMap;
import java.util.Map;

public class PermissionsContainer {

private static Map<PermissionType, String> permissions;
	
	static {
		permissions = new HashMap<PermissionType, String>();
		permissions.put(PermissionType.COMMAND, "powerdispenser.cmd");
	}
	
	public static String getPermission(PermissionType pt) {
		return permissions.get(pt);
	}
}
