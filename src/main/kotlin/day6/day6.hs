import Data.List.Split (splitOn)
import Data.List (union, intersect)

main1 :: String -> Int
main1 xs = sum $ length <$> (foldl1 union . lines) <$> splitOn "\n\n" xs

main2 :: String -> Int
main2 xs = sum $ length <$> (foldl1 intersect . lines) <$> splitOn "\n\n" xs

main = readFile "inputs/2020/6.txt" >>= print . main2