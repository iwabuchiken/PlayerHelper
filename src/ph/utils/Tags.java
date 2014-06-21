package ph.utils;

public class Tags {

	public static enum DialogButtonTags {
		// Generics
		DLG_GENERIC_DISMISS, dlg_generic_dismiss_second_dialog,
		dlg_generic_dismiss_third_dialog,
		
		// dlg_actionbar_mainactv.xml
		DLG_ACTIONBAR_MAINACTV_BT_OK,
		
		
	}//public static enum DialogTags
	
	public static enum DialogItemTags {
		// dlg_moveFiles(Activity actv)
		dlg_move_files,
		
		// dlg_add_memos.xml
		dlg_add_memos_gv,

		// dlg_db_admin.xml
		DLG_DB_ADMIN_LV,

		// dlg_admin_patterns.xml
		dlg_admin_patterns_lv,

		// dlg_delete_patterns.xml
		dlg_delete_patterns_gv,

		// dlg_moveFiles_search(Activity actv)
		dlg_move_files_search,

		// main_opt_menu_admin
		main_opt_menu_admin,
		
		// dlg_bmactv_list_long_click
		DLG_BMACTV_LIST_LONGCLICK,
		
	}//public static enum DialogItemTags
	
	
	public static enum ButtonTags {
		// MainActv.java
		ib_up,
		ACTVMAIN_BT_START, ACTVMAIN_BT_STOP,
		
		// DBAdminActivity.java
		db_manager_activity_create_table, db_manager_activity_drop_table, 
		db_manager_activity_register_patterns,
		
	}//public static enum ButtonTags
	
	public static enum ItemTags {
		
		// MainActivity.java
		dir_list,
		
		// ThumbnailActivity.java
		dir_list_thumb_actv,
		
		// Methods.java
		dir_list_move_files,
		
		// TIListAdapter.java
		tilist_checkbox,
		
		// SearchActv.java
		dir_list_actv_search,
		
		// actv_hist.xml
		dir_list_actv_hist,
		
	}//public static enum ItemTags

//	public static enum MoveMode {
//		// TIListAdapter.java
//		ON, OFF,
//		
//	}//public static enum MoveMode

	public static enum PrefenceLabels {
		
		CURRENT_PATH,
		
		thumb_actv,
		
		chosen_list_item,
		
	}//public static enum PrefenceLabels

	public static enum ListTags {
		// MainActivity.java
		actv_main_lv,
		
		// BMActv.java
		actv_bm_lv,
		
	}//public static enum ListTags

	public static enum TVTags {
		
		// PlayActv
		PLAYACTV_TITLE,
	}
}//public class Tags
