# Austin Tab
:sheep:  A light weight tab layout for Android :sheep:

<img src="/images/examples.png" alt="drawing" width="500"/>

## Examples
### A basic example
1. Add `AustinTabView` to a layout file

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    
    <me.austinng.austintab.AustinTabView
        android:id="@+id/austinTabView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        app:indicator_style="SEGMENTED_CONTROL"
        app:tab_container_padding="4dp"
        app:tab_style="ADAPTIVE"
        app:tab_badge_font="@font/ubuntu_medium"
        app:tab_text_font_active="@font/ubuntu_medium"
        app:tab_text_font_inactive="@font/ubuntu_regular" />
        
</LinearLayout>
```

2. In your activity

```
austinTabView.setData(
    listOf(
        TabData("Home", icon = R.drawable.ic_home),
        TabData("Notifications", badge = "3"),
        TabData("Profile"),
    )
)

austinTabView.setTabSelectedListener { index ->
    Log.d("AustinTabView", "Index $index selected")
}

austinTabView.setTabReselectedListener { index ->
    Log.d("AustinTabView", "Index $index reselected")
}

austinTabView1.setTabUnSelectedListener { index ->
    Log.d("AustinTabView", "Index $index unselected")
}
```

### Other examples
You can find additional examples of usage and customization [here](app/src/main/java/me/austinng/austintab).

## Anatomy

![Anatomy](/images/anatomy.png "Anatomy")

## Installation

1. Add `Jitpack Repository` to your `settings.gradle` file:

```
maven { url 'https://jitpack.io' }
```

2. In your app’s `build.gradle` file, add the dependency:

```
dependencies {
	implementation 'com.github.austin17ng:austin-tab:1.0.0'
}
```

## List of attributes

| Attributes  | Default value | Description |
| ------------- |:-------------:|:-------------:|
| `offset`    | `16dp`     | Space between the tab and screen edges   |
| `enable_auto_scroll`     | `true`     | Automatically centers the selected tab on the screen.  |
|   `enable_animation`   |   `false`  |   Enables tab translation animation  |
|   `tab_container_background`   |     |    Background of the tab container |
|     `tab_container_padding` |  `0dp`   |   Padding for the tab container  |
|  `tab_style`    |  `ADAPTIVE`   | Tab style: `ADAPTIVE` or `EQUAL`    |
|     `indicator_style` |   `TAB`  | Indicator style: `TAB` or `SEGMENTED_CONTROL`    |
|    `indicator_src`  |     |    Drawable resource for the indicator|
|`indicator_height`|`2dp`|Height of the indicator (only for `indicator_style = TAB`)|
|`tab_vertical_padding`|`6dp`|Vertical padding for tabs|
|`tab_horizontal_padding`|`16dp`|Horizontal padding for tabs|
|`tab_text_color_active`|`white` (for `TAB`), `black` (for `SEGMENTED`)|Text color of the active tab|
|`tab_text_color_inactive`|`black`|Text color of inactive tabs|
|`tab_text_font_active`|`none`|Font for the active tab|
|`tab_text_font_inactive`|`none`|Font for inactive tabs|
|`tab_text_size`|`14sp`|Size of the tab text|
|`tab_icon_active_color`|`white` (for `TAB`), `black` (for `SEGMENTED`)|Color for the active tab icon|
|`tab_icon_inactive_color`|`black`|Color for the inactive tab icon|
|`tab_badge_text_size`|`10sp`|Text size of the badge|
|`tab_badge_font`|`none`|Font for the tab badge|
|`tab_badge_source`||Drawable resource for the badge|
|`tab_badge_color`|`white`|Text color of the badge|
|`tab_badge_position`|`TOP`|Position of the badge: `TOP`, `CENTER`, or `BOTTOM`|

## List of functions

`setData(data: List<TabData>)`

`setTabSelectedListener(listener: (index: Int) -> Unit)`

`setTabReselectedListener(listener: (index: Int) -> Unit)`

`setTabUnSelectedListener(listener: (index: Int) -> Unit)`

`attachWithViewPager2(viewPager2: ViewPager2)`

`getIndex(): Int`

`setIndex(index: Int)`

## Contributing

Feel free to contribute by submitting issues or pull requests. And please ensure that all pull requests are checked out from the `master` branch.

## License

Austin Tab is licensed under the MIT License.
