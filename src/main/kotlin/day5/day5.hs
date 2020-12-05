import Data.List



main = readFile "inputs/2020/5.txt" >>= print . main2


getID :: String -> Int
getID str = row * 8 + col
    where row = fromBinary [if c == 'F' then '0' else '1' | c <- take 7 str]
          col = fromBinary [if c == 'L' then '0' else '1' | c <- drop 7 str]

fromBinary :: [Char] -> Int
fromBinary [] = 0
fromBinary ['1'] = 1
fromBinary ['0'] = 0
fromBinary xs = (fromBinary $ init xs) * 2 + case last xs of { '0' -> 0; '1' -> 1; }

main1 :: String -> Int
main1 x = maximum $ getID <$> lines x

main2 :: String -> Int
main2 x = head [ a | (a, b) <- zip l (tail l), a+ 1 /= b]
    where l = sort $ getID <$> lines x