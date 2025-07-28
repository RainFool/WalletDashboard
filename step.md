 # Android钱包仪表板项目 - 分布操作步骤

## 项目概述
实现一个简单的钱包仪表板应用，支持BTC、ETH和CRO三种货币，使用Kotlin和响应式编程（Coroutine Flow）。

## 技术栈要求
- 语言：Kotlin
- 响应式编程：Coroutine Flow
- 架构：MVVM + Repository模式
- 网络请求：模拟网络请求
- 图片加载：Glide

## 分布操作步骤

### 第一阶段：项目基础设置（Day 1 - 上午）

#### 1.1 项目初始化
- [ ] 创建新的Android项目
- [ ] 配置Kotlin支持
- [ ] 设置Git仓库并初始化提交
- [ ] 配置基础依赖（Retrofit、Coroutines、Glide等）

#### 1.2 数据模型设计
- [ ] 创建Currency数据类
  ```kotlin
  data class Currency(
      val coinId: String,
      val name: String,
      val symbol: String,
      val tokenDecimal: Int,
      val colorfulImageUrl: String,
      val grayImageUrl: String
  )
  ```
- [ ] 创建ExchangeRate数据类
  ```kotlin
  data class ExchangeRate(
      val fromCurrency: String,
      val toCurrency: String,
      val rate: Double,
      val timeStamp: Long
  )
  ```
- [ ] 创建WalletBalance数据类
  ```kotlin
  data class WalletBalance(
      val currency: String,
      val amount: Double
  )
  ```
- [ ] 创建WalletDashboard数据类（聚合数据）
  ```kotlin
  data class WalletDashboard(
      val currencies: List<Currency>,
      val exchangeRates: List<ExchangeRate>,
      val walletBalances: List<WalletBalance>
  )
  ```

#### 1.3 网络层设计 ✅
- [x] 创建API接口
  ```kotlin
  interface WalletApi {
      @GET("currencies")
      suspend fun getCurrencies(): CurrenciesResponse
      
      @GET("live-rates")
      suspend fun getLiveRates(): LiveRatesResponse
      
      @GET("wallet-balance")
      suspend fun getWalletBalance(): WalletBalanceResponse
  }
  ```
- [x] 配置模拟API实现（MockWalletApi）
- [x] 创建网络响应数据类
- [x] 创建NetworkService服务类
- [x] 创建NetworkProvider依赖提供类

### 第二阶段：数据层实现（Day 1 - 下午）

#### 2.1 Repository层
- [ ] 创建WalletRepository接口
  ```kotlin
  interface WalletRepository {
      fun getWalletDashboard(): Flow<Result<WalletDashboard>>
      fun getCurrencies(): Flow<Result<List<Currency>>>
      fun getExchangeRates(): Flow<Result<List<ExchangeRate>>>
      fun getWalletBalances(): Flow<Result<List<WalletBalance>>>
  }
  ```
- [ ] 实现WalletRepositoryImpl
- [ ] 添加错误处理和重试机制
- [ ] 实现数据缓存策略

#### 2.2 数据源管理
- [ ] 创建本地JSON数据源
- [ ] 实现数据解析逻辑
- [ ] 添加数据验证
- [ ] 实现数据转换器

#### 2.3 依赖注入配置
- [ ] 配置Hilt模块
- [ ] 提供网络依赖
- [ ] 提供Repository依赖

### 第三阶段：业务逻辑层（Day 2 - 上午）

#### 3.1 ViewModel实现
- [ ] 创建MainViewModel
  ```kotlin
  class MainViewModel @Inject constructor(
      private val walletRepository: WalletRepository
  ) : ViewModel() {
      private val _uiState = MutableStateFlow<WalletUiState>(WalletUiState.Loading)
      val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()
      
      fun loadWalletDashboard()
      fun refreshData()
  }
  ```
- [ ] 实现数据加载逻辑
- [ ] 添加错误处理
- [ ] 实现数据刷新功能

#### 3.2 UI状态管理
- [ ] 创建UI状态数据类
  ```kotlin
  sealed class WalletUiState {
      object Loading : WalletUiState()
      data class Success(val dashboard: WalletDashboard) : WalletUiState()
      data class Error(val message: String) : WalletUiState()
  }
  ```
- [ ] 实现状态转换逻辑
- [ ] 添加加载状态管理

#### 3.3 业务计算逻辑
- [ ] 实现USD余额计算
  ```kotlin
  fun calculateUsdBalance(balance: WalletBalance, rate: ExchangeRate): Double {
      return balance.amount * rate.rate
  }
  ```
- [ ] 实现总余额计算
- [ ] 添加货币格式化

### 第四阶段：UI层实现（Day 2 - 下午）

#### 4.1 布局设计
- [ ] 创建activity_main.xml
- [ ] 设计钱包卡片布局
- [ ] 实现货币列表布局
- [ ] 添加总余额显示区域

#### 4.2 RecyclerView适配器
- [ ] 创建CurrencyAdapter
- [ ] 实现ViewHolder
- [ ] 添加点击事件处理
- [ ] 实现数据绑定

#### 4.3 UI组件实现
- [ ] 实现货币图标加载
- [ ] 添加余额显示格式化
- [ ] 实现刷新功能
- [ ] 添加错误状态显示

### 第五阶段：功能完善和优化（Day 3 - 上午）

#### 5.1 错误处理
- [ ] 实现网络错误处理
- [ ] 添加数据验证
- [ ] 实现用户友好的错误提示
- [ ] 添加重试机制

#### 5.2 性能优化
- [ ] 实现数据缓存
- [ ] 优化图片加载
- [ ] 添加内存管理
- [ ] 实现懒加载

#### 5.3 用户体验
- [ ] 添加加载动画
- [ ] 实现下拉刷新
- [ ] 添加空状态处理
- [ ] 优化响应式布局

### 第六阶段：测试和文档（Day 3 - 下午）

#### 6.1 单元测试
- [ ] 测试ViewModel逻辑
- [ ] 测试Repository层
- [ ] 测试数据转换逻辑
- [ ] 测试错误处理

#### 6.2 集成测试
- [ ] 测试网络请求
- [ ] 测试UI交互
- [ ] 测试数据流

#### 6.3 文档和清理
- [ ] 编写README文档
- [ ] 清理未使用的代码
- [ ] 优化项目结构
- [ ] 准备Git提交

## 关键实现要点

### 1. 响应式编程实现
```kotlin
// 使用Flow实现数据流
fun getWalletDashboard(): Flow<Result<WalletDashboard>> = flow {
    try {
        val currencies = getCurrencies()
        val rates = getExchangeRates()
        val balances = getWalletBalances()
        
        val dashboard = WalletDashboard(currencies, rates, balances)
        emit(Result.success(dashboard))
    } catch (e: Exception) {
        emit(Result.failure(e))
    }
}
```

### 2. 错误处理策略
- 网络错误：显示重试按钮
- 数据错误：显示错误信息
- 空数据：显示空状态
- 加载状态：显示加载动画

### 3. 架构考虑
- 使用MVVM模式
- 实现Repository模式
- 使用依赖注入
- 遵循单一职责原则

### 4. 扩展性设计
- 支持添加新货币
- 支持多语言
- 支持主题切换
- 支持数据持久化

## 提交检查清单

- [ ] 代码能够编译运行
- [ ] 使用Kotlin语言
- [ ] 使用Coroutine Flow实现响应式编程
- [ ] 实现BTC、ETH、CRO三种货币支持
- [ ] 正确计算USD余额
- [ ] 处理错误场景
- [ ] 代码结构清晰
- [ ] 移除未使用的代码
- [ ] 创建Git仓库并提交代码
- [ ] 编写项目文档

## 时间安排

**Day 1:**
- 上午：项目设置和数据模型
- 下午：网络层和Repository层

**Day 2:**
- 上午：ViewModel和业务逻辑
- 下午：UI实现

**Day 3:**
- 上午：功能完善和优化
- 下午：测试和文档

总计：3天完成项目