package at.arz.latte.framework.admin.restapi;

import java.util.function.Function;

import at.arz.latte.framework.admin.Group;
import at.arz.latte.framework.admin.Permission;
import at.arz.latte.framework.admin.User;
import at.arz.latte.framework.restapi.GroupData;
import at.arz.latte.framework.restapi.PermissionData;
import at.arz.latte.framework.restapi.UserData;

/**
 * class with helper functions for mapping JPA models to REST DAO
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class AdminMapper {

	public static final Function<Group, GroupData> MAP_TO_GROUPDATA = new Function<Group, GroupData>() {
		@Override
		public GroupData apply(Group group) {
			return new GroupData(group.getId(), group.getName());
		}
	};

	public static final Function<Group, GroupData> MAP_TO_GROUPDETAIL = new Function<Group, GroupData>() {
		@Override
		public GroupData apply(Group group) {
			GroupData data = new GroupData(group.getId(), group.getName());
			if (group.getPermissions() != null) {
				for (Permission permission : group.getPermissions()) {
					data.addPermission(MAP_TO_PERMISSIONDATA.apply(permission));
				}
			}
			return data;
		}
	};

	public static final Function<Permission, PermissionData> MAP_TO_PERMISSIONDATA = new Function<Permission, PermissionData>() {
		@Override
		public PermissionData apply(Permission permission) {
			return new PermissionData(permission.getId(), permission.getName());
		}
	};

	public static final Function<User, UserData> MAP_TO_USERDATA = new Function<User, UserData>() {

		@Override
		public UserData apply(User user) {
			return new UserData(user.getId(),
								user.getFirstName(),
								user.getLastName(),
								user.getEmail());
		}
	};

	public static final Function<User, UserData> MAP_TO_USERDETAIL = new Function<User, UserData>() {

		@Override
		public UserData apply(User user) {
			UserData data = new UserData(	user.getId(),
											user.getFirstName(),
											user.getLastName(),
											user.getEmail());
			data.setPassword(user.getPassword());
			if (user.getGroup() != null) {
				for (Group group : user.getGroup()) {
					data.addGroup(MAP_TO_GROUPDATA.apply(group));
				}
			}

			return data;
		}
	};

}
