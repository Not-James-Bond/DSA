/*
# The scenario is that we are moving into e-scooters
# Each scooter in our customers e-scooter fleet needs to have a unique code assigned,
# the regulators will determine that codes should be generated according to a scheme
# that varies by city and region.
# Fortunately the schemes are similar enough that the regulators can define them
# by providing character sequences to pick from, e.g.
    paris_naming_scheme = [
                            "*#&",
                            "2345678",
                            ]
    london_naming_scheme = [
                            "abcdefghjklmnpqvwxy",
                            "abcdefghjklmnpqvwxy",
                            "-/",
                            "0123"
                            ]
# So for london example, we would see the output
# aa-0, aa-1, aa-2, aa-3, aa/0, aa/1, aa/2, aa/3, ab-0, and so on

*/

/**
 * Generate scooter codes based on a given naming scheme.
 * 
 * @param {Array} namingScheme - The naming scheme used to generate the codes.
 * @param {number} count - The number of codes to generate.
 * @returns {Array} - An array of generated scooter codes.
 */
function generateScooterCodes(namingScheme, count) {
    /**
     * Generate codes recursively based on the naming scheme.
     * 
     * @param {Array} parts - The parts of the naming scheme.
     * @param {number} index - The current index of the naming scheme.
     * @param {string} currentCode - The current generated code.
     * @returns {Array} - An array of generated codes.
     */
    function generateCodeRecursively(parts, index, currentCode) {
        if (index >= parts.length) {
            return [currentCode.split('').reverse().join('')];
        }

        const scheme = namingScheme[index];
        const schemeLength = scheme.length;
        const codes = [];

        for (let i = 0; i < schemeLength; i++) {
            const code = currentCode + scheme[i];
            codes.push(...generateCodeRecursively(parts, index + 1, code));
        }

        return codes;
    }

    const codes = generateCodeRecursively(namingScheme, 0, '');
    return codes.slice(0, count);
}
  
  // Example usage for the london_naming_scheme
  const londonNamingScheme2 = [
    "abcdefghjklmnpqvwxy",
    "abcdefghjklmnpqvwxy",
    "-/",
    "0123"
  ];
  
  const count2 = 20;
  const codes2 = generateScooterCodes(londonNamingScheme2, count2);
  
  console.log(codes2);
// */  

/**
 * Generates scooter codes based on a naming scheme.
 * 
 * @param {Array} namingScheme - The naming scheme for the codes.
 * @param {number} count - The number of codes to generate.
 * @returns {Array} - The generated scooter codes.
 */
function generateScooterCodes(namingScheme, count) {
  /**
   * Recursively generates permutations of the naming scheme parts.
   * 
   * @param {Array} parts - The naming scheme parts.
   * @param {number} index - The current index in the naming scheme.
   * @param {Array} currentPermutation - The current permutation being generated.
   * @param {Array} result - The array to store the generated permutations.
   */
  function permute(parts, index, currentPermutation, result) {
    if (index >= parts.length) {
      result.push(currentPermutation.join(''));
      return;
    }

    const scheme = namingScheme[index];
    for (let i = 0; i < scheme.length; i++) {
      currentPermutation[index] = scheme[i];
      permute(parts, index + 1, currentPermutation, result);
    }
  }

  const result = [];
  permute(namingScheme, 0, Array(namingScheme.length), result);
  return result.slice(0, count);
}

// Example usage for the london_naming_scheme
const londonNamingScheme3 = [
    "abcdefghjklmnpqvwxy",
    "abcdefghjklmnpqvwxy",
    "-/",
    "0123"
];

const count3 = 20;
const codes3 = generateScooterCodes(londonNamingScheme3, count3);

console.log(codes3);
// */


/**
 * Generates scooter codes based on the provided naming scheme and count.
 *
 * @param {Array} namingScheme - An array of strings representing the naming scheme for the codes.
 * @param {number} count - The number of codes to generate.
 * @return {Array} An array of strings representing the generated codes.
 */
function generateScooterCodes(namingScheme, count) {
    const codes = [];
    const schemeLengths = namingScheme.map(scheme => scheme.length);

    for (let i = 0; i < count; i++) {
        let code = '';
        let index = i;
        console.log("++++++++++++++++++++++++++++")
        for (let j = namingScheme.length - 1; j >= 0; j--) {
            const schemeLength = schemeLengths[j];
            console.log("schemeLength:: ",schemeLength);
            const schemeIndex = index % schemeLength;
            console.log("schemeIndex:: ",schemeIndex);
            index = Math.floor(index / schemeLength);
            console.log("index:: ",index);
            code += namingScheme[j][schemeIndex];
            console.log("code:: ",code, "\n---------------------------");
        }

        codes.push(code.split('').reverse().join(''));
    }

    return codes;
}

// Example usage for the london_naming_scheme
const londonNamingScheme = [
    "abcdefghjklmnpqvwxy",
    "abcdefghjklmnpqvwxy",
    "-/",
    "0123"
];

const count = 20;
const codes = generateScooterCodes(londonNamingScheme, count);

console.log(codes);
