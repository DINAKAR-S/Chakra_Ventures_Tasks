# Chakra Ventures - Task 1 App

This Android application was developed as part of Chakra Ventures Task 1.

The app performs 3 main functions across separate tabs using `ViewPager2` and `TabLayout`:

1. **Connect to a Local REST API** and display a list of books and IPL teams
2. **Request Camera and Microphone Permissions** at runtime
3. **Display all user-installed apps** on the device

---

## Tech Stack

- **Android SDK** (Java / Kotlin)
- **Jetpack Components**: ViewPager2, TabLayout, Fragments
- **HTTP**: OkHttp (for API calls)
- **Permissions**: AndroidX Permission APIs
- **UI**: Material Design + XML Layout + Custom Drawable Styling

---

### How to Run the Project

### 1. Run Flask Backend

```bash
python app.py
```

- Make sure to update the IP address in your Android code (e.g. `http://192.168.xx.xx:5000`)
- Endpoints available:
  - `/books`
  - `/ipl-teams`

### 2. Run Android App

- Open the project in **Android Studio**
- Plug in a device or start an emulator
- Click **Run**
- Navigate through the tabs:
  - Tab 1: See data from the REST API
  - Tab 2: Request permissions
  - Tab 3: View installed apps

---

## UI Customizations

- Blue background across all fragments
- White cards with rounded corners and borders
- Clean padding and spacing
---

## Requirements

- Android Studio (Arctic Fox or above)
- Android SDK 24+
- Python 3.7+ with Flask

---
