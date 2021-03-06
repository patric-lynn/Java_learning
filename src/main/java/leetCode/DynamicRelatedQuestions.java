package leetCode;

/**
 * Description
 * 5. 最长回文子串: 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。[求最长公共子串与判断]
 * 45. 跳跃游戏 II: 使用最少的跳跃次数到达数组的最后一个位置。[贪心算法或动规]
 * 53. 找到最大和的连续子数组
 * 62. 向下和向右到达有下角的不同路径
 * 63. 向下和向右到达有下角的不同路径 II 带障碍物
 * 64. 二位数组中最小路径长度
 * 70. 青蛙爬楼梯
 * 121. 买卖股票的最佳时机
 * @author Lynn-zd
 * @date Created on 2020/3/13 23:38
 */
public class DynamicRelatedQuestions {
    /**
     * 5. 最长回文子串
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     *
     * 示例 1：
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     *
     * 根据回文串的定义，正着和反着读一样，那我们是不是把原来的字符串倒置了，然后找最长的公共子串就可以了。
     * 例如 S = "caba" ，S = "abac"，最长公共子串是 "aba"，所以原字符串的最长回文串就是 "aba"。
     * 再看一个例子，S="abc435cba"，S="abc534cba"，最长公共子串是 "abc" 和 "cba"，但很明显这两个字符串都不是回文串。
     * 所以我们求出最长公共子串后，并不一定是回文串，我们还需要判断该字符串倒置前的下标和当前的字符串下标是不是匹配。
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s.equals(""))
            return "";
        String origin = s;
        String reverse = new StringBuffer(s).reverse().toString();
        int length = s.length();
        int[][] arr = new int[length][length];
        int maxLen = 0;
        int maxEnd = 0;
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++) {
                if (origin.charAt(i) == reverse.charAt(j)) {
                    if (i == 0 || j == 0) {
                        arr[i][j] = 1;
                    } else {
                        arr[i][j] = arr[i - 1][j - 1] + 1;
                    }
                }
                /**********修改的地方*******************/
                if (arr[i][j] > maxLen) {
                    int beforeRev = length - 1 - j;
                    if (beforeRev + arr[i][j] - 1 == i) { //判断下标是否对应
                        maxLen = arr[i][j];
                        maxEnd = i;
                    }
                    /*************************************/
                }
            }
        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }


    /**
     * 45. 跳跃游戏 II
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     * 示例:
     *
     * 输入: [2,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。
     *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
     * 说明:
     * 假设你总是可以到达数组的最后一个位置。
     *
     * 动态方程为:dp[i] = min(dp[i], dp[j] + 1),j位置可以到达i 的位置
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        //如果数组为空或长度为0则立即返回，否则新建动态规划所需的整型数组
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        // 数组初始化
        dp[0] = 0;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (nums[j] >= i - j) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[nums.length - 1];
    }


    /**
     * 53. 找到最大和的连续子数组
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     *
     * 示例:
     *
     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     * 解法一：动态规划方法，向前推进
     * 注意：二选一判断逻辑一定要加括号，因为：运算符优先级太低，会被后边的+运算符连接！！！！
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        //前置条件
        if(nums == null || nums.length == 0) return 0;
        //定义状态数组与初始值
        //int[] dp = new int[nums.length]; //本题可以省略状态数组，节省空间为o[1]
        int curNum = nums[0];
        int maxNum = nums[0];
        //定义状态转移方程
        for(int i=1; i<nums.length; i++){
            curNum = ((0 > curNum) ? 0:curNum) + nums[i];
            maxNum = (maxNum > curNum) ? maxNum : curNum;
        }
        return maxNum;
    }


    /**
     * 62. 向下和向右到达有下角的不同路径
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     *
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     *
     * 问总共有多少条不同的路径？
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        if( m==0 || n==0) return 0; //pre
        int[][] dp = new int[m][n]; //init
        for(int i=0; i<n; i++){     //value
            dp[0][i] = 1;
        }
        for(int i=0; i<m; i++){
            dp[i][0] = 1;
        }
        for(int i=1; i<m; i++){     //transfer
            for(int j=1; j<n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    /**
     * 63. 不同路径 II
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
     *说明：m 和 n 的值均不超过 100。
     *
     * 示例 1:
     *
     * 输入:
     * [
     *   [0,0,0],
     *   [0,1,0],
     *   [0,0,0]
     * ]
     * 输出: 2
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid[0].length == 0) {
            return 0;
        }
        int rol = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        for (int i = 0; i < rol; i++) {
            for (int j = 0; j < col; j++) {
                // 判断是否有障碍物，若有，当前点对结果贡献为0，直接置0即可。
                if (obstacleGrid[i][j] == 1) {
                    obstacleGrid[i][j] = 0;
                    continue;
                }
                if (i == 0 && j == 0) {
                    obstacleGrid[i][j] = 1; // 将第一个格点赋初始值1；
                } else if (i == 0) {
                    obstacleGrid[i][j] = obstacleGrid[i][j - 1]; // 第一行格点值等于左边格点值；
                } else if (j == 0) {
                    obstacleGrid[i][j] = obstacleGrid[i - 1][j]; // 第一列格点值等于上边格点值；
                } else {
                    obstacleGrid[i][j] = obstacleGrid[i][j - 1] + obstacleGrid[i - 1][j]; // 其他格点值等于左边、上边格点值之和；
                }
            }
        }
        return obstacleGrid[rol - 1][col - 1];
    }

    /**
     * 64. 二位数组中最小路径长度
     * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     * 说明：每次只能向下或者向右移动一步。
     * 示例:
     * 输入:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * 输出: 7
     * 解释: 因为路径 1→3→1→1→1 的总和最小。
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(i == 0 && j == 0) continue;
                else if(i == 0)  grid[i][j] = grid[i][j - 1] + grid[i][j];
                else if(j == 0)  grid[i][j] = grid[i - 1][j] + grid[i][j];
                else grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];
    }

    /**
     * 70. 爬楼梯
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     *
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     *
     * 注意：给定 n 是一个正整数。
     *
     * 示例 1：
     *
     * 输入： 2
     * 输出： 2
     * 解释： 有两种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶
     * 2.  2 阶
     * 示例 2：
     *
     * 输入： 3
     * 输出： 3
     * 解释： 有三种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶 + 1 阶
     * 2.  1 阶 + 2 阶
     * 3.  2 阶 + 1 阶
     *
     * @param n
     * @return
     */
    public static int climbStairs(int n) {
        if(n <= 2){
            return n;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for(int i=2; i<n; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n-1];
    }




    /**
     * 121. 买卖股票的最佳时机
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     *
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
     *
     * 注意你不能在买入股票前卖出股票。
     *
     * 示例 1:
     *
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
     * 示例 2:
     *
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for(int i=0; i<prices.length; i++){
            if(prices[i]<minPrice){
                minPrice = prices[i];
            }else if(prices[i]-minPrice > maxProfit){
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        DynamicRelatedQuestions dynamicRelatedQuestions = new DynamicRelatedQuestions();

        String s = "ababcdedcbef";


        System.out.println(dynamicRelatedQuestions.climbStairs(30));
        System.out.println(dynamicRelatedQuestions.longestPalindrome(s));

    }
}
