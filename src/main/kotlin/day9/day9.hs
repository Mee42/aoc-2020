
import Data.List
import Debug.Trace (trace)
import Data.Maybe

main1 :: Int -> [Int] -> Int
main1 count xs = if isValid then main1 count (tail xs) else current
    where current = xs!!count
          isValid = any (\[a, b]->a+b==current) $ permutation 2 (take count xs)

permutation :: Int -> [a] -> [[a]]
permutation 0 xs = [[]  | x <- xs]
permutation 1 xs = [[x] | x <- xs]
permutation n xs = permutation (n - 1) xs >>= \list -> (:list) <$> xs

example = [35,20,15,25,47,40,62,55,65,95,102,117,150,182,127,219,299,277,309,576]

-- travelingAdd [a, b, c, d, ...]
-- => [a, a+b, a+b+c, a+b+c+d, ...]
travelingAdd :: [Int] -> [Int]
travelingAdd [] = []
travelingAdd (x:xs) = (:) x $ (+x) <$> travelingAdd xs

data ReachedTarget

main2 :: Int -> [Int] -> Int
main2 target xs = if isNothing findResult then main2 target (tail xs) else answer
    where findResult = find ((>= target).snd) (zip [0..] (travelingAdd xs)) >>= \(index, sum) -> if sum > target then Nothing else Just index
          answer = minimum xxs + maximum xxs
              where (Just index) = findResult
                    xxs = take index xs




--main = readFile "inputs/2020/9.txt" >>= print . main1 25 . map read . lines
main = readFile "inputs/2020/9.txt" >>= print . main2 675280050 . map read . lines
