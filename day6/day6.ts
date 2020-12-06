import {input, testInput} from './day6.data';

const groupToObject = (groupObj, person: string) => {
    if(!groupObj['answers'])
        groupObj['answers'] = new Map<String, number>();

    person.split('')
        .map((answer) =>
            groupObj['answers'][answer] = groupObj['answers'][answer] ? groupObj['answers'][answer] + 1 : 1);

    groupObj['groupSize'] = groupObj['groupSize'] ? groupObj['groupSize'] + 1 : 1;
    return groupObj;
}

const countAllYes = (count: number, group): number => {
    console.log(`Count all yes: ${JSON.stringify(group)}`)
    let groupAllYes = 0;
    for(const answer in group['answers']) {
        if(group['answers'][answer] === group.groupSize)
            groupAllYes++
    }
    return count + groupAllYes;
}

const part2 =
    input
        .split('\n\n')// split into groups
        .map(group => group
            .trim()
            .split('\n')
            .reduce(groupToObject, {}))
        .reduce(countAllYes, 0)

console.log(`${JSON.stringify(part2)}`);

console.log(`Answer to part 2: ${part2}`);
