# Use Case Description

## Record match scores

<table>
  <tr>
    <td><strong>Use Case ID</strong></td>
    <td colspan="3">1.5</td>
  </tr>
  <tr>
    <td><strong>Use Case Name</strong></td>
    <td colspan="3">Record match scores</td>
  </tr>
  <tr>
    <td><strong>Created by</strong></td>
    <td>Reda Al Sulais</td>
    <td><strong>Last Updated by</strong></td>
    <td>Reda Al Sulais</td>
  </tr>
  <tr>
    <td><strong>Date Created</strong></td>
    <td>30/03/2023</td>
    <td><strong>Last Revesion Date</strong></td>
    <td>05/04/2023</td>
  </tr>
  <tr>
    <td><strong>Actors</strong></td>
    <td colspan="3">Administrator</td>
  </tr>
  <tr>
    <td><strong>Description</strong></td>
    <td colspan="3">Record match scores</td>
  </tr>
  <tr>
    <td><strong>Trigger</strong></td>
    <td colspan="3">Click button</td>
  </tr>
  <tr>
    <td><strong>Preconditions</strong></td>
    <td colspan="3">
      <ul>
        <li>The user has logged in.</li>
        <li>The user is authrized with needed permissions.</li>
        <li>The tournament isn't archived.</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><strong>Postconditions</strong></td>
    <td colspan="3"></td>
  </tr>
  <tr>
    <td><strong>Normal Flow</strong></td>
    <td colspan="3">
      <ol>
        <li>Select a tournament.</li>
        <li>Select a match that doesn't have any scores yet.</li>
        <li>Type the match scores.</li>
        <li>Ask for confirmation.</li>
        <li>Set match scores.</li>
      </ol>
    </td>
  </tr>
  <tr>
    <td><strong>Alternative Flows</strong></td>
    <td colspan="3">
      <ul>
        <li>
          In step 3 of the normal flow, if the tournament is Elimination type
          and the score is a tie:
          <ol>
            <li>Ask the administrator to record extra time scores.</li>
            <li>Continue other steps</li>
          </ol>
        </li>
        <li>
          In step 4 of the normal flow, if the actor didn't confirm:
          <ol>
            <li>Go back to step 3.</li>
          </ol>
        </li>
        <li>
          After finishing the normal flow, if the recorded scores are for the
          last match:
          <ol>
            <li>Set tournament winner.</li>
          </ol>
        </li>
        <li>
          After finishing the normal flow, if the recorded scores aren't for
          the last match and the tournament is Elimination type:
          <ol>
            <li>Set next match participants.</li>
          </ol>
        </li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><strong>Exceptions</strong></td>
    <td colspan="3"></td>
  </tr>
  <tr>
    <td><strong>Assumptions</strong></td>
    <td colspan="3"></td>
  </tr>
  <tr>
    <td><strong>Notes and Issues</strong></td>
    <td colspan="3">
      <ul>
        <li>Ranking System:
          <ul>
            <li>Elimination tournament type:<br>
              This tournament is pyramid-like matches where:
              <ul>
                <li>If a team win, they go to the next stage.</li>
                <li>
                  If a team lose, they are eliminated from the tournament.
                </li>
                <li>
                  If two teams draw, extra time well be given until one team
                  win.
                </li>
              </ul>
              The winner is the one that wins the last match.
            </li>
            <li>Round robin tournament type:<br>
              Each team will play against all other teams over time.
              <ul>
                <li>If a team win, they gain 3 points.</li>
                <li>If a team lose, they gain 0 points.</li>
                <li>If two team draws, give both teams 1 point.</li>
              </ul>
              The winner is team with most scores, in case there was a tie in
              terms of points between two teams we look into
              <ol>
                <li>The result of match between those two teams.</li>
                <li>The team with highest wins.</li>
                <li>The team who scored most goals/points.</li>
                <li>The team who received least goals/points.</li>
              </ol>
            </li>
          </ul>
        </li>
      </ul>
    </td>
  </tr>
</table>

<!--
<table>
  <tr>
    <td><strong>Use Case ID</strong></td>
    <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Use Case Name</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Created by</strong></td>
      <td></td>
      <td><strong>Last Updated by</strong></td>
      <td></td>
  </tr>
  <tr>
      <td><strong>Date Created</strong></td>
      <td></td>
      <td><strong>Last Revesion Date</strong></td>
      <td></td>
  </tr>
  <tr>
      <td><strong>Actors</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Description</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Trigger</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Preconditions</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Postconditions</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Normal Flow</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Alternative Flows</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Exceptions</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Assumptions</strong></td>
      <td colspan="3"></td>
  </tr>
  <tr>
      <td><strong>Notes and Issues</strong></td>
      <td colspan="3"></td>
  </tr>
</table>
-->
