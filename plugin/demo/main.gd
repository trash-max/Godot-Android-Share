extends Node2D

# TODO: Update to match your plugin's name
var _plugin_name = "GodotAndroidShare"
var _android_plugin


func _ready():
	if Engine.has_singleton(_plugin_name):
		_android_plugin = Engine.get_singleton(_plugin_name)
	else:
		printerr("Couldn't find plugin " + _plugin_name)


func _on_Button_pressed():
	if _android_plugin:
		# TODO: Update to match your plugin's API
		_android_plugin.shareText("txtTitle", "txtSubject", "txtText")


func _on_button_2_pressed() -> void:
#	var crop = $paletteList.get_rect()
	await RenderingServer.frame_post_draw
	var img: Image = get_viewport().get_texture().get_image()
	# user://tmp.png
#	var img_save_path: String = OS.get_user_data_dir() + "/palette.png"
	#var img_save_path = "user://palette.png"
	var img_save_path = "/media/one.allme.android.share/files/palette.png"
	var global_path = ProjectSettings.globalize_path(img_save_path)
	
	img.save_png(img_save_path)
	print("Try share " + global_path)
	# if share was found, use it
	if _android_plugin:
		_android_plugin.sharePic(img_save_path, "txtTitle", "txtSubject", "txtText")

