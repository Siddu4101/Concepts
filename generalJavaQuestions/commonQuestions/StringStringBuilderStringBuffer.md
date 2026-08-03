# String vs StringBuilder vs StringBuffer

## Comparison Table

| Feature | String | StringBuilder | StringBuffer |
|---------|--------|---------------|--------------|
| **Thread Safety** | ✓ Safe | ✗ Not Safe | ✓ Safe (Synchronized) |
| **Mutability** | Immutable | Mutable | Mutable |
| **Memory Location** | String Pool / Heap | Heap | Heap |
| **Performance** | Slow | Fast | Slower (sync overhead) |
| **Modification Behavior** | Creates new instance on every modification | Modifies in-place | Modifies in-place |
| **Synchronization** | Thread-safe by nature | No synchronization | All methods are synchronized |
| **Best For** | Constants and immutable values | Single-threaded code (loops, concatenation) | Multi-threaded environments |
