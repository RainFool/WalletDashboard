# WalletDashboard

一个基于Android传统View系统的钱包仪表板应用。

## 项目配置

### 编译环境
- **Java版本**: Java 8 (1.8)
- **Android Gradle Plugin**: 7.4.2
- **Kotlin版本**: 1.8.10

### 主要依赖库
- **Kotlin**: 1.8.10
- **AndroidX Core KTX**: 1.10.1
- **AndroidX AppCompat**: 1.6.1
- **AndroidX ConstraintLayout**: 2.1.4
- **AndroidX Lifecycle**: 2.5.1
  - lifecycle-runtime-ktx
  - lifecycle-viewmodel-ktx
- **AndroidX Activity KTX**: 1.7.0
- **Material Design**: 1.9.0

### 项目结构
```
app/
├── src/main/java/com/rainfool/wallet/
│   ├── MainActivity.kt          # 主Activity
│   └── ui/
│       └── MainViewModel.kt     # 主ViewModel
├── src/main/res/layout/
│   └── activity_main.xml        # 主Activity布局
└── build.gradle                # 应用模块配置
```

### 构建配置
项目已配置为使用Java 8编译，使用传统XML布局：
- `compileOptions`: Java 1.8
- `kotlinOptions`: JVM 1.8
- 使用ConstraintLayout进行布局
- 使用Material Design组件
- 移除了所有Compose相关依赖

### 运行项目
```bash
./gradlew build
./gradlew installDebug
```

## 功能特性
- 使用传统Android View系统构建UI
- 采用MVVM架构模式
- 使用ViewModel管理UI状态
- 使用ConstraintLayout进行响应式布局
- 支持响应式编程（Coroutine Flow）

## 技术栈
- **UI**: 传统XML布局 + ConstraintLayout
- **架构**: MVVM + ViewModel
- **响应式**: Coroutine Flow
- **依赖注入**: ViewModels by viewModels()

## 注意事项
- 项目使用Java 8编译，确保在Java 8+环境下运行
- 使用传统View系统，不依赖Compose
- 使用AppCompat主题确保兼容性
- 项目已优化为最小依赖配置，只包含必要的库 