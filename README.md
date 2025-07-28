# WalletDashboard

一个基于Android Jetpack Compose的钱包仪表板应用。

## 项目配置

### 编译环境
- **Java版本**: Java 8 (1.8)
- **Android Gradle Plugin**: 7.4.2
- **Kotlin版本**: 1.8.10
- **Compose编译器版本**: 1.4.3

### 主要依赖库
- **Kotlin**: 1.8.10
- **AndroidX Core KTX**: 1.10.1
- **AndroidX Lifecycle**: 2.5.1
  - lifecycle-runtime-ktx
  - lifecycle-viewmodel-ktx
- **Jetpack Compose**: 2023.08.00 BOM
  - compose-ui
  - compose-material3
  - compose-activity

### 项目结构
```
app/
├── src/main/java/com/rainfool/wallet/
│   ├── MainActivity.kt          # 主Activity
│   ├── ui/
│   │   └── MainViewModel.kt     # 主ViewModel
│   └── ui/theme/               # 主题相关文件
└── build.gradle                # 应用模块配置
```

### 构建配置
项目已配置为使用Java 8编译，确保兼容性：
- `compileOptions`: Java 1.8
- `kotlinOptions`: JVM 1.8
- 移除了不必要的依赖库，只保留核心的Kotlin和ViewModel库

### 运行项目
```bash
./gradlew build
./gradlew installDebug
```

## 功能特性
- 使用Jetpack Compose构建现代化UI
- 采用MVVM架构模式
- 使用ViewModel管理UI状态
- 支持响应式编程

## 注意事项
- 项目使用Java 8编译，确保在Java 8+环境下运行
- 某些Compose库的lint检查可能显示警告（因为库是用Java 17编译的），但不影响功能
- 项目已优化为最小依赖配置，只包含必要的库 