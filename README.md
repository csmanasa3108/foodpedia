# Compromised inventory batches

`Result.countCompromisedBatches` counts every contiguous subarray whose bitwise
OR is equal to at least one batch code present in the complete input array.

For each array position, the implementation keeps the distinct OR values of all
subarrays ending at the previous position. Equal values are grouped together
with the number of subarrays that produce them, so duplicate subarrays are still
counted. Adding a new element ORs it into each previous group and also creates a
new length-one subarray.

Because extending a subarray can only add set bits, the number of distinct OR
values at each position is bounded by the number of bits in an integer. The
algorithm therefore runs in `O(n * 32)` time and uses `O(32 + n)` space (the
`n` term is the set of batch codes).
